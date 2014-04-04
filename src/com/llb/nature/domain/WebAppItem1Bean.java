package com.llb.nature.domain;

public class WebAppItem1Bean {
	private String imageUrl;
	private String title;
	private String content;
	private boolean ifAdd;//是否添加的标记
	
	public WebAppItem1Bean(String imageUrl,String title,String content,boolean ifAdd){
		this.imageUrl=imageUrl;
		this.title=title;
		this.content=content;
		this.ifAdd=ifAdd;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isIfAdd() {
		return ifAdd;
	}
	public void setIfAdd(boolean ifAdd) {
		this.ifAdd = ifAdd;
	}
}
