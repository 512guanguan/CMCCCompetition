package com.llb.nature.domain;

public class NewsItem1Bean {
	private String imageUrl;
	private String title;
	private String content;
	private String time;
	
	public NewsItem1Bean(String imageUrl, String title, String content,
			String time) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
		this.content = content;
		this.time = time;
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
