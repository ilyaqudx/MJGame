package com.gwsoft.server.game.command;

import org.apache.mina.core.session.IoSession;

import com.google.protobuf.InvalidProtocolBufferException;
import com.gwsoft.server.game.UserContext;
import com.gwsoft.server.game.domain.GameUser;
import com.gwsoft.server.game.domain.MessageRequest;
import com.gwsoft.server.game.message.sender.ResponseMessageSender;
import com.gwsoft.server.game.protobuf.UserProtobuf.User;

public class LoginCommand extends AbstructCommand {

	@Override
	public void execute(IoSession session,MessageRequest request) {
		System.out.println("handle Login logic");
		if(null == request)
			return;
		
		byte[] body = request.getBody();
		try {
			User user = User.parseFrom(body);
			GameUser player = new GameUser();
			player.setName(user.getName());
			player.setId(request.getUid());
			System.out.println("user = "+player);
			
			player.setSession(session);
			UserContext.getInstance().addOnlineUser(player);
			ResponseMessageSender.responseLogin(player, true);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
			ResponseMessageSender.responseLogin(null, false);
		}
	}
}
