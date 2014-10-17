package com.gwsoft.server.game.domain;

import java.util.Arrays;

public class MessageResponse {
	
	public static final byte SUCCESS = 1;
	public static final int  FAIL = 0;
	
	public MessageResponse(short responseID,byte success, byte[] body) {
		this.responseID = responseID;
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
	private short responseID;
	public short getResponseID() {
		return responseID;
	}
	public void setResponseID(short responseID) {
		this.responseID = responseID;
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
		return "MessageResponse [responseID=" + responseID + ", body="
				+ Arrays.toString(body) + ", bodyLength=" + bodyLength + "]";
	}
}
