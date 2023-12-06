package com.liyang.luckySheet.socket.session;

import javax.websocket.Session;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <h2>Excel用户连接Session</h2>
 * <p>
 *
 * </p>
 *
 * @author 作者<1 9 2 2 8 0 2 3 5 2 @ qq.com>
 * @since 2023年12月05日 16:55
 */
public class ExcelUserSession {
	/**
	 * 当前连接的用户ID
	 */
	private String uid;
	
	/**
	 * 当前加入的表格组
	 */
	private String gridKey;
	
	/**
	 * 连接时间
	 */
	private String dateTime;
	
	/**
	 * WebSocketSession连接对象
	 */
	private Session session;
	
	
	/**
	 * 发送消息给当前连接用户
	 */
	private void sendMessage(String data){
		session.getAsyncRemote().sendText(data);
	}
	
	
	public ExcelUserSession(String uid, String gridKey, Session session) {
		this.uid = uid;
		this.session = session;
		this.gridKey = gridKey;
		this.dateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	}
	
	public String getGridKey() {
		return gridKey;
	}
	
	public void setGridKey(String gridKey) {
		this.gridKey = gridKey;
	}
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
}
