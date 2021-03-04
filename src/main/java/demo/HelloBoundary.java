package demo;

import java.util.Date;

// JSON Demo: {"message":"hello"}
public class HelloBoundary {
	private String message;
	private Date currentTimestamp;
	
	public HelloBoundary() {
		this.currentTimestamp = new Date();
	}

	public HelloBoundary(String message) {
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
