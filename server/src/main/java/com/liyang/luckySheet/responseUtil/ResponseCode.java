package com.liyang.luckySheet.responseUtil;

public enum ResponseCode {
	SUCCESS(200, "请求成功"),
	
	Created(201,"新增成功"),
	
	Updated(203,"修改成功"),
	
	Deleted(204,"删除成功"),

    ERROR(500, "请求失败");


    private final Integer code;

    private final String message;

     ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
