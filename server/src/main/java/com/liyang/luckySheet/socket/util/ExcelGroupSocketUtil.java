package com.liyang.luckySheet.socket.util;

import com.liyang.luckySheet.constant.DefaultGridKeyEmum;
import com.liyang.luckySheet.socket.session.ExcelUserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h2>Excel协同编辑用户组操作工具</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年11月25日 14:27
 */
public class ExcelGroupSocketUtil {
	
	
	private static final  Logger LOGGER = LoggerFactory.getLogger(ExcelGroupSocketUtil.class);
	
	/**
	 * 在线Excel协同编辑连接组 <br>
	 *
	 * 键为 Excel表格唯一标识符, 值为 用户连接Map集合
	 * 用户连接Map：Key(用户ID) - Value({@link ExcelUserSession})
	 */
	private static final  ConcurrentHashMap<String, ConcurrentHashMap<String, ExcelUserSession>> EXCEL_SYNERGY_GROUP;
	
	
	static{
		// 系统启动 初始化 空在线Excel协同编辑连接组
		EXCEL_SYNERGY_GROUP = new ConcurrentHashMap<>(64);
		
		ConcurrentHashMap<String, ExcelUserSession> defaultGridKey = new ConcurrentHashMap<>(32);
		
		// 默认Excel表格
		EXCEL_SYNERGY_GROUP.put(DefaultGridKeyEmum.DefaultGridKey.getGridKey(), defaultGridKey);
		LOGGER.info("系统启动，初始化空在线Excel协同编辑连接组");
	}
	
	
	/**
	 * 用户加入指定协同表格连接组
	 *
	 * @param gridKey 表格唯一标识符
	 * @param session socket连接对象{@link ExcelUserSession}
	 */
	public static void joinExcelSynergyGroup(String gridKey, ExcelUserSession session){
		if(EXCEL_SYNERGY_GROUP.containsKey(gridKey)){
			EXCEL_SYNERGY_GROUP.get(gridKey).put(session.getUid(), session);
		}else{
			ConcurrentHashMap<String, ExcelUserSession> hashMap = new ConcurrentHashMap<>();
			hashMap.put(session.getUid(), session);
			EXCEL_SYNERGY_GROUP.put(gridKey, hashMap);
		}
		
		// 移除当前用户已加入的其他群组
		EXCEL_SYNERGY_GROUP.forEach((key, value) -> {
			//排除当前加入的组
			if (!key.equalsIgnoreCase(gridKey)) {
				// 断开连接
				try {
					value.get(session.getUid()).getSession().close();
					value.remove(session.getUid());
				} catch (Exception ignored) {
				}
			}
		});
	}
	
	
	/**
	 * 用户断开指定协同表格连接组
	 *
	 * @param session 连接对象{@link Session}
	 */
	public static void disconnectExcelSynergyGroup(Session session){
		
		
		// 新建一个集合存储当前用户加入的群组名以及用户名
		List<HashMap<String, String>> gridKeys = new LinkedList<>();
		
		// 根据 uid查找所在群组, 遍历所有群组
		for (Map.Entry<String, ConcurrentHashMap<String, ExcelUserSession>> stringConcurrentHashMapEntry : EXCEL_SYNERGY_GROUP.entrySet()) {
			String gridKey = stringConcurrentHashMapEntry.getKey();
			
			// 遍历当前群组的用户集合
			for (Map.Entry<String, ExcelUserSession> stringSocketSessionEntry : stringConcurrentHashMapEntry.getValue().entrySet()) {
				
				if(stringSocketSessionEntry.getValue().getSession().getId().equalsIgnoreCase(session.getId())){
					HashMap<String, String> hashMap = new HashMap<>(2);
					hashMap.put("gridKey", gridKey);
					hashMap.put("uid", stringSocketSessionEntry.getValue().getUid());
					gridKeys.add(hashMap);
					break;
				}
			}
		}
		
		// 移除并且断开
		gridKeys.forEach(k ->{
			
			try {
				String gridKey = k.get("gridKey");
				String uid = k.get("uid");
				// 断开连接
				session.close();
				
				// 移除用户
				EXCEL_SYNERGY_GROUP.get(gridKey).remove(uid);
				
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			
		});
	}
	
	/**
	 * 根据Session查找用户
	 *
	 * @param session 连接对象{@link Session}
	 */
	public static ExcelUserSession findUserBySession(Session session){
		// 如果查询到的用户有多个，那么就是用户加入群组的时候没有退出其他群组，所以会有多个，这个时候需要根据加入时间倒序，返回时间最大的那个
		final LinkedList<ExcelUserSession> groupSessions = new LinkedList<>();
		
		// 遍历所有表格群组，查找所有
		EXCEL_SYNERGY_GROUP.forEach((key, value) -> {
			// 遍历当前群组，查找
			value.forEach((k, v) ->{
				if(v.getSession().getId().equals(session.getId())){
					groupSessions.add(v);
				}
			});
		});
		ExcelUserSession groupSession = null;
		
		if(!CollectionUtils.isEmpty(groupSessions)){
			groupSessions.sort(new Comparator<ExcelUserSession>() {
				@Override
				public int compare(ExcelUserSession o1, ExcelUserSession o2) {
					return o1.getDateTime().compareTo(o2.getDateTime());
				}
			});
			groupSession = groupSessions.get(0);
		}
		
		return groupSession;
	}
	
	/**
	 * 向指定组内所有用户发送群组所有用户列表
	 *
	 * @param gridKey 表格唯一标识符
	 */
	public static void sendGroupUserMessage(String gridKey, String data){
		
		if(EXCEL_SYNERGY_GROUP.containsKey(gridKey) && !EXCEL_SYNERGY_GROUP.get(gridKey).isEmpty()){
			
			EXCEL_SYNERGY_GROUP.get(gridKey).forEach((key, value) -> value.getSession().getAsyncRemote().sendText(data));
		}
		
	}
	
}
