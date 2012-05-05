package com.wanderluster.db;

import java.sql.Timestamp;

public class DbRecord {
	
	private Integer cid;
	private String title;
	private String content;
	private Timestamp lastmodify;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
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
	public Timestamp getLastmodify() {
		return lastmodify;
	}
	public void setLastmodify(Timestamp lastmodify) {
		this.lastmodify = lastmodify;
	}

}
