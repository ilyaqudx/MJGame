package com.gwsoft.client;

import java.util.Arrays;

public class MessageResponse {

	private short responseID;
	private long uid;
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	private byte[] body;
	private int bodyLength;
	public MessageResponse(short responseID,long uid, byte[] body) {
		this.responseID = responseID;
		this.uid = uid;
		setBody(body);
	}
	public short getResponseID() {
		return responseID;
	}
	public void setResponseID(short responseID) {
		this.responseID = responseID;
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
		return "MessageResponse [responseID=" + responseID + ", body="
				+ Arrays.toString(body) + ", bodyLength=" + bodyLength + "]";
	}
}
