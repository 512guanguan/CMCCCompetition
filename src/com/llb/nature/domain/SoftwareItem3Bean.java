package com.llb.nature.domain;

public class SoftwareItem3Bean {
	private String imageUrl;
	private String title;
	private float starRating;//软件评级
	private String downloadTime;//下载次数
	private String size;//软件包大小
	private boolean ifDownload;//是否已经下载的标记
	
	public SoftwareItem3Bean(String imageUrl, String title, float starRating,
			String downloadTime, String size, boolean ifDownload) {
		super();
		this.imageUrl = imageUrl;
		this.title = title;
		this.starRating = starRating;
		this.downloadTime = downloadTime;
		this.size = size;
		this.ifDownload = ifDownload;
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
	public float getStarRating() {
		return starRating;
	}
	public void setStarRating(float starRating) {
		this.starRating = starRating;
	}
	public String getDownloadTime() {
		return downloadTime;
	}
	public void setDownloadTime(String downloadTime) {
		this.downloadTime = downloadTime;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public boolean isIfDownload() {
		return ifDownload;
	}
	public void setIfDownload(boolean ifDownload) {
		this.ifDownload = ifDownload;
	}		
}
