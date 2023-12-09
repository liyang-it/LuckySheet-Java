package com.liyang.luckySheet.socket.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <h2>WebSocket消息返回格式</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年11月25日 16:02
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class SocketMessageResult {
	/**
	 * 状态码说明：<br>
	 *
	 * <li>2500 连接失败</li>
	 * <li>2501 连接成功</li>
	 * <li>2502 接收当前所在群组所有用户列表消息</li>
	 */
	private Integer code;
	private String msg;
	private Object data;
	
	public SocketMessageResult(Integer code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	
	public static String joinFail() {
		return new SocketMessageResult(2500, "连接协议错误或者系统异常，系统断开此次连接", null).toString();
	}
	
	public static String joinSuccess() {
		return new SocketMessageResult(2501, "连接成功", null).toString();
	}
	
	public static String sendGroupUserList(Object data) {
		return new SocketMessageResult(2502, null, data).toString();
	}
	
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
	
	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getData() {
		return data;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
}
