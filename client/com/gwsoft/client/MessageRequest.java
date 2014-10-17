package com.gwsoft.client;

import java.util.Arrays;

public class MessageRequest {
	
	public static final byte SUCCESS = 1;
	public static final int  FAIL = 0;
	
	public MessageRequest(short requestID,byte success, byte[] body) {
		this.requestID = requestID;
		this.success = success;
		setBody(body);
	}

	private byte success;
	public byte getSuccess() {
		return success;
	}
	public void setSuccess(byte success) {
		this.success = success;
	}
	private short requestID;
	public short getRequestID() {
		return requestID;
	}
	public void setRequestID(short requestID) {
		this.requestID = requestID;
	}

	private byte[] body;
	private int bodyLength;
	
	public byte[] getBody() {
		return body;
	}
	public void setBody(byte[] body) {
		this.body = body;
		if(null != body)
			setBodyLength(body.length);
	}
	
	public void setBodyLength(int length){
		this.bodyLength = length;
	}
	
	public int getBodyLength(){
		return this.bodyLength;
	}
	
	public int getResponseLength(){
		return 3 + bodyLength;
	}
	@Override
	public String toString() {
		return "MessageRequest [responseID=" + requestID + ", body="
				+ Arrays.toString(body) + ", bodyLength=" + bodyLength + "]";
	}

}
