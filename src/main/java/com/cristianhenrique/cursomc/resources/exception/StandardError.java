package com.cristianhenrique.cursomc.resources.exception;

public class StandardError {
	private Integer Status;
	private String msg;
	private Long timeStamp;
	
	
	
	public StandardError() {
		super();
	}



	public StandardError(Integer status, String msg, Long timeStamp) {
		super();
		Status = status;
		this.msg = msg;
		this.timeStamp = timeStamp;
	}



	public Integer getStatus() {
		return Status;
	}



	public void setStatus(Integer status) {
		Status = status;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}



	public Long getTimeStamp() {
		return timeStamp;
	}



	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
	
	
	
	
	

}
