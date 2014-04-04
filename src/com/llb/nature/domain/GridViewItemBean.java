package com.llb.nature.domain;


public class GridViewItemBean {
	private String imageUrl;//图片的地址
	private String name;
	private String url;
	
	public GridViewItemBean(String imageUrl,String name,String url){
		this.url=url;
		this.imageUrl=imageUrl;
		this.name=name;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
