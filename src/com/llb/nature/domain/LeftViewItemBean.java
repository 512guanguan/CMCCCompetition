package com.llb.nature.domain;

public class LeftViewItemBean {
//	private int itemImage;//listview item的图片
	private String itemName;//栏目名称
	public LeftViewItemBean(String itemName){
		this.itemName=itemName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
