package com.liyang.luckySheet.socket;

import com.alibaba.fastjson.JSONObject;
import com.liyang.luckySheet.service.ExcelWorkSheetService;
import com.liyang.luckySheet.socket.session.ExcelUserSession;
import com.liyang.luckySheet.socket.util.ExcelGroupSocketCopyUtil;
import com.liyang.luckySheet.socket.util.ExcelGroupSocketUtil;
import com.liyang.luckySheet.socket.util.SocketMessageResult;
import com.liyang.luckySheet.socket.util.luckySheetUtils.Pako_GzipUtils;
import com.liyang.luckySheet.utils.SpringUtils;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <h2>Excel协同编辑WebSocket组件，处理LuckySheet Socket修改更新操作</h2>
 * <p>
 * <a href="https://docs.spring.io/spring-framework/reference/web/websocket.html">Spring-WebSocket官方文档</a><br>
 * <p>
 * WebSocket连接地址： 127.0.0.1:12581/luk/ws-excel
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年12月05日 16:52
 */

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ServerEndpoint(value = "/ws-excel")
public class WebSocketExcelHandler {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(WebSocketExcelHandler.class);
	
	public ExcelWorkSheetService service = SpringUtils.getBean(ExcelWorkSheetService.class);
	
	
	/**
	 * WebSocket 连接成功调用方法
	 *
	 * @param session 连接对象
	 */
	@OnOpen
	public void onOpen(Session session) throws IOException {
		try {
			// 获取请求参数, 请确保一个参数在url上只出现一次,如果参数出现多次则需要使用 getQueryString解析获取
			
			// getRequestParameterMap已经将Url参数转成了map集合，键为参数，value为参数值集合，因为参数可能会有多个，例如： ?a=1&a=2&a=3&b=3
			Map<String, List<String>> requestParameter = session.getRequestParameterMap();
			
			// 表格唯一标识符,不能为空, 如果是LuckySheet连接会自动传递 "g"参数(前提是LuckySheet组件的gridKey配置不为空)，如果自己的WebSocket连接请手动传递
			String gridKey = requestParameter.get("g").get(0);
			
			// 自定义参数,用于区分连接组是副本(专门接收当前组人员列表变动消息) 1、Excel协同编辑群组 2、副本群组
			String ly = requestParameter.get("ly").get(0);
			
			// 自定义参数传递用户名
			String uid = requestParameter.get("u").get(0);
			
			ExcelUserSession socketSession = new ExcelUserSession(uid, gridKey, session);
			
			if ("1".equals(ly)) {
				// 加入Excel协同编辑群组
				ExcelGroupSocketUtil.joinExcelSynergyGroup(gridKey, socketSession);
				//
				session.getAsyncRemote().sendText("1");
				
			} else {
				// 为空，不加入Excel协同群组，加入到一个Excel协同群组副本里面，专门用来接收Excel协同群组人员列表变动消息，因为前端需要单独获取用户列表独立一个Socket连接
				// 而LuckySheet操作也是独立一个Socket连接，两个连接不能放在同一个Excel群组里面，因为map会覆盖重复键的连接
				// 加入 副本 Excel协同编辑群组
				
				ExcelGroupSocketCopyUtil.joinExcelSynergyGroup(gridKey, socketSession);
			}
			
			
		} catch (Exception e) {
			// 没有达成连接协议或者连接异常，断开当前Session连接
			session.getAsyncRemote().sendText(SocketMessageResult.joinFail());
			session.close();
			LOGGER.error("【协同编辑 WebSocket】连接失败,原因：", e);
		}
	}
	
	/**
	 * 接收普通文本消息, 副本连接不发送消息，只监听服务发送消息
	 *
	 * @param message 消息
	 * @param session 当前socket连接对象
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		if (StringUtils.isBlank(message)) {
			return;
		}
		// 根据Session查找用户
		ExcelUserSession groupSession = ExcelGroupSocketUtil.findUserBySession(session);
		
		if (groupSession == null) {
			LOGGER.info("【协同编辑 WebSocket】处理客户端消息异常，原因没有找到Session对应的用户, Session-[{}]", session.getId());
			return;
		}
		if ("rub".equals(message)) {
			LOGGER.info("【协同编辑 WebSocket】 保持连接状态信息");
		} else {
			String contentReal = Pako_GzipUtils.unCompressToURI(message);
			LOGGER.info("【协同编辑 WebSocket】消息解压LuckySheet压缩内容：{}", contentReal);
			
			JSONObject bson = null;
			try {
				bson = JSONObject.parseObject(contentReal);
				if (bson == null) {
					throw new SecurityException("将消息内容转换json为空");
				}
			} catch (Exception e) {
				session.getAsyncRemote().sendText("消息格式不正确！");
				LOGGER.info("【协同编辑 WebSocket】处理客户端消息异常，原因: {}", e.getMessage());
				return;
			}
			
			//返回消息类型type :0：连接成功，1.发送给发送信息用户，2.发送信息给其他用户，3.发送选区位置信息 999、用户连接断开
			HashMap<String, Object> operationalResult = new HashMap<>();
			
			// 发送给其他协同用户的操作结果消息
			HashMap<String, Object> otherOperationalResult = new HashMap<>();
			
			String gridKey = groupSession.getGridKey();
			
			// 根据不同操作指令做出响应, LuckySheet表格操作消息内容字段解释：https://mengshukeji.gitee.io/LuckysheetDocs/zh/guide/operate.html#%E5%8D%95%E5%85%83%E6%A0%BC%E5%88%B7%E6%96%B0
			ReentrantLock lock = new ReentrantLock();
			try {
				if (lock.tryLock()) {
					if ("v".equals(bson.getString("t"))) {
						
						// 单元格内容变更
						service.updateCellData(gridKey, bson);
						LOGGER.info("【协同编辑 WebSocket】处理 单元格内容变更成功");
						
					} else if ("cg".equals(bson.getString("t"))) {
						
						// 单元格属性配置变更
						service.updateCellConfig(gridKey, bson);
						LOGGER.info("【协同编辑 WebSocket】处理 单元格属性配置变更成功");
						
					} else if ("rv".equals(bson.getString("t"))) {
						
						// 范围单元格刷新，例如更改整个列的内容居中等, 这里的范围指的是在行或者列是连续的，例如 使用 Ctrl 选中多个单元格不属于范围，使用Shift 选中多个单元格才算
						// 例如 同时修改第一行 第一列、第三列数据，虽然是同时修改两列，但并不是连续的，不属于单元格范围，属于单元格内容变更，会发起两次单元格内容变更指令
						// 例如 同时修改第一行 第一列、第二列、第三列数据(行号或者列序号必须是有序连在一块的) 这个就属于单元格范围更新，范围是只 开始到结束有序的范围，例如 1,2,3,4,5,6,7,8,9,10 这样才算
						// 例如 同时修改第一行 第一列、第二行 第一列数据(行号或者列序号必须是有序连在一块的) 这个就属于单元格范围更新，范围是只 开始到结束有序的范围，例如 1,2,3,4,5,6,7,8,9,10 这样才算
						service.updateMultipleCellConfig(gridKey, bson);
						LOGGER.info("【协同编辑 WebSocket】处理 范围单元格内容变更成功");
						
					} else {
						LOGGER.info("【协同编辑 WebSocket】未实现该指令：{}", bson.getString("t"));
					}
				}
			} catch (Exception e) {
				LOGGER.error("【协同编辑 WebSocket】处理 Excel变更异常，原因", e);
			} finally {
				if (lock.isHeldByCurrentThread()) {
					lock.unlock();
				}
			}
			
			// 操作消息发送给当前连接的用户
			operationalResult.put("createTime", System.currentTimeMillis());
			operationalResult.put("data", contentReal);
			operationalResult.put("id", session.getId());
			operationalResult.put("returnMessage", "success");
			operationalResult.put("status", "0");
			operationalResult.put("type", 1);
			operationalResult.put("username", groupSession.getUid());
			
			// 操作消息发送给其他用户连接的用户
			otherOperationalResult.put("createTime", System.currentTimeMillis());
			otherOperationalResult.put("data", contentReal);
			otherOperationalResult.put("returnMessage", "success");
			otherOperationalResult.put("status", "0");
			otherOperationalResult.put("type", 2);
			
			session.getAsyncRemote().sendText(JSONObject.toJSONString(operationalResult));
			
			// 群发消息给其他用户
			ExcelGroupSocketUtil.sendGroupUserMessage(gridKey, JSONObject.toJSONString(otherOperationalResult));
			
		}
	}
	
	/**
	 * 关闭连接调用的方法
	 */
	@OnClose
	public void onClose(Session session) {
		try {
			ExcelGroupSocketUtil.disconnectExcelSynergyGroup(session);
			ExcelGroupSocketCopyUtil.disconnectExcelSynergyGroup(session);
		} catch (Exception ignored) {
		
		}
	}
	
	@OnError
	public void onError(Session session, Throwable error) {
		LOGGER.error("【协同编辑 WebSocket】异常, Session: {} 原因: ", session.getId(), error);
	}
	
	
}
