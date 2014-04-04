package com.llb.nature.domain;

public class WebAppItem2Bean {
	private String imageUrl;
	private String title;
	private String content;
	private int type;//标记类别，方便跳转
	public WebAppItem2Bean(String imageUrl, String title, String content,
			int type) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
		this.content = content;
		this.type = type;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}
