package demo;

import java.util.Date;

// JSON Demo: {"message":"hello"}
public class TwinsBoundary {
	private String message;
	private Date currentTimestamp;
	
	public TwinsBoundary() {
		this.currentTimestamp = new Date();
	}

	public TwinsBoundary(String message) {
		this();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getCurrentTimestamp() {
		return currentTimestamp;
	}
	
	public void setCurrentTimestamp(Date currentTimestamp) {
		this.currentTimestamp = currentTimestamp;
	}
	
}
