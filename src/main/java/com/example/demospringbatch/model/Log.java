package com.example.demospringbatch.model;

import java.util.Date;

public class Log {

	private Long id;
	private Date accessDate;
	private String accessIp;
	private String requestType;
	private String status;
	private String userAgent;
	
	public Log() {
		super();
	}

	public Log(Date accessDate, String accessIp, String requestType, String status, String userAgent) {
		super();
		this.accessDate = accessDate;
		this.accessIp = accessIp;
		this.requestType = requestType;
		this.status = status;
		this.userAgent = userAgent;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getAccessDate() {
		return accessDate;
	}
	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}
	
	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", accessDate=" + accessDate + ", accessIp=" + accessIp + ", requestType="
				+ requestType + ", status=" + status + ", userAgent=" + userAgent + "]";
	}
}
