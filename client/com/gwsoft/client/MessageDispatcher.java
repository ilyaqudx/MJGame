package com.gwsoft.client;

import org.apache.mina.core.session.IoSession;

import com.gwsoft.server.game.protobuf.ReadyProtobuf.Ready;
import com.gwsoft.server.game.utils.ReqIDConstant;
import com.gwsoft.server.game.utils.ResIDConstant;

public class MessageDispatcher {

	public static MessageDispatcher INSTANCE = new MessageDispatcher();
	
	public void dispatch(MessageRequest request,IoSession session) throws Exception{
		System.out.println("entry dispatch");
		short id = request.getRequestID();
		System.out.println("id : "+(id == ResIDConstant.RESPONSE_LOGIN));
		switch (id) {
		case ResIDConstant.RESPONSE_LOGIN:
			MessageResponse respone = new MessageResponse(ReqIDConstant.ENTERROOM, GameClientMain.userid, null);
			session.write(respone);
			break;
		case ResIDConstant.RESPONSE_ENTERROOM:
			byte[] body = Ready.newBuilder().setReady(1).build().toByteArray();
			MessageResponse response = new MessageResponse(ReqIDConstant.Ready, GameClientMain.userid, body);
			session.write(response);
			break;
		case ReqIDConstant.Ready:
			break;
		}
	}
}
