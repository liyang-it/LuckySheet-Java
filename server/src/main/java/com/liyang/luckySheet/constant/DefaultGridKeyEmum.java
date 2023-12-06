package com.liyang.luckySheet.constant;

/**
 * 默认Excel表格gridKey枚举
 * @author liyang
 */
public enum DefaultGridKeyEmum {
	DefaultGridKey("defaultGridKey", "默认表格唯一标识符");
	
	private String gridKey;
	private String description;
	
	DefaultGridKeyEmum(String gridKey, String description) {
		this.gridKey = gridKey;
		this.description = description;
	}
	
	public String getGridKey() {
		return gridKey;
	}
	
	public void setGridKey(String gridKey) {
		this.gridKey = gridKey;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
