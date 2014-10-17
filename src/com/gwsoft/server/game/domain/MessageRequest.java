package com.gwsoft.server.game.domain;

import java.util.Arrays;

public class MessageRequest {

	private short requestID;
	private long uid;
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	private byte[] body;
	private int bodyLength;
	public MessageRequest(short requestID,long uid, byte[] body) {
		this.requestID = requestID;
		this.uid = uid;
		setBody(body);
	}
	public short getRequestID() {
		return requestID;
	}
	public void setRequestID(short requestID) {
		this.requestID = requestID;
	}
	
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
		return 2 + 8 + bodyLength;
	}
	@Override
	public String toString() {
		return "MessageRequest [requestID=" + requestID + ", body="
				+ Arrays.toString(body) + ", bodyLength=" + bodyLength + "]";
	}
}
