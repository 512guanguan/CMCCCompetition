package com.llb.nature.domain;


public class LeftViewItemBean {
	private int itemImage;//listview item的图片
	private String itemName;//栏目名称
	public LeftViewItemBean(String itemName,int itemImage){
		this.itemName=itemName;
		this.itemImage=itemImage;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemImage() {
		return itemImage;
	}
	public void setItemImage(int itemImage) {
		this.itemImage = itemImage;
	}
	
}
