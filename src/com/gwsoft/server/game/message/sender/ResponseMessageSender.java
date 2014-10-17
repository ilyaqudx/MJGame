package com.gwsoft.server.game.message.sender;

import com.gwsoft.server.game.GameServerManager;
import com.gwsoft.server.game.domain.GameUser;
import com.gwsoft.server.game.domain.MessageResponse;
import com.gwsoft.server.game.protobuf.UserProtobuf.User;
import com.gwsoft.server.game.utils.ResIDConstant;

public class ResponseMessageSender implements MessageSender{

	private static User createTestUserInfo(){
		return User.newBuilder().setId(10000).setName("ÕÅÈý").setSex(1).
			setAge(20).setEmail("abc@qq.com").build();
	}
	
	
	public static void responseLogin(GameUser user,boolean loginResult){
		if(user == null)
			return;
		
		User userbuf =  createTestUserInfo();
		
		MessageResponse response = new MessageResponse(ResIDConstant.RESPONSE_LOGIN, 
				loginResult?SUCCESS:FAIL,userbuf.toByteArray());
		
		GameServerManager.INSTANCE.send2Client(user.getSession(), response);
	}
	
	public static void resposneEnterRoom(GameUser user,boolean enterResult){
		if(user == null)
			return;
		MessageResponse response = new MessageResponse(ResIDConstant.RESPONSE_ENTERROOM, 
				enterResult?SUCCESS:FAIL,null);
		GameServerManager.INSTANCE.send2Client(user.getSession(), response);
	}
	
	
	public static void responseReady(GameUser user){
		if(user == null)
			return;
		MessageResponse response = new MessageResponse(ResIDConstant.RESPONSE_READY, 
				SUCCESS,null);
		GameServerManager.INSTANCE.send2Client(user.getSession(), response);
	}
}
