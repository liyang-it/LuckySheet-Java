package com.liyang.luckySheet.responseUtil;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 请求响应体
 */
@Data
@Accessors(chain = true)
public class ResponseUtil implements Serializable, Cloneable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 创建一个源数据，后期 clone 的源数据
	 */
	public static final ResponseUtil single = new ResponseUtil();
	
	private int code;
	
	private String msg;
	
	private Object data;
	
	/**
	 * 构造函数私有化 不允许new
	 */
	private ResponseUtil() {}
	
	
	private ResponseUtil(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/*
	 * 使用单例模式 获取克隆对象
	 *
	 * @return {@link OperationLog}
	 */
	private static ResponseUtil getInstance() {
		try {
			return single.clone();
		} catch (CloneNotSupportedException e) {
			return new ResponseUtil();
		}
	}
	
	/**
	 * 重写克隆方法
	 *
	 * @return
	 */
	public ResponseUtil clone() throws CloneNotSupportedException {
		return (ResponseUtil) super.clone();
	}
	


	public static ResponseUtil success() {
		return getInstance()
				.setCode(ResponseCode.SUCCESS.code())
				.setMsg(ResponseCode.SUCCESS.message())
				.setData(null);
				
	}
	
	public static ResponseUtil success(Object data) {
		return getInstance()
				.setCode(ResponseCode.SUCCESS.code())
				.setMsg(ResponseCode.SUCCESS.message())
				.setData(data);
	}
	
	public static ResponseUtil success(String msg, Object data) {
		return getInstance()
				.setCode(ResponseCode.SUCCESS.code())
				.setMsg(msg)
				.setData(data);
	}
	
	
	public static ResponseUtil fail(String msg) {
		return getInstance()
				.setCode(ResponseCode.ERROR.code())
				.setMsg(msg)
				.setData(null);
	}
	
	public static ResponseUtil fail(String msg, Object data) {
		return getInstance()
				.setCode(ResponseCode.ERROR.code())
				.setMsg(msg)
				.setData(data);
	}
	
	public static ResponseUtil fail() {
		return getInstance()
				.setCode(ResponseCode.ERROR.code())
				.setMsg(ResponseCode.ERROR.message())
				.setData(null);
	}
	
	public static ResponseUtil build(int code, String msg, Object data) {
		return getInstance()
				.setCode(code)
				.setMsg(msg)
				.setData(data);
	}
	

}
