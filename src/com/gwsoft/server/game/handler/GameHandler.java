package com.gwsoft.server.game.handler;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.gwsoft.server.game.GameServerMain;
import com.gwsoft.server.game.command.AbstructCommand;
import com.gwsoft.server.game.domain.MessageRequest;

public class GameHandler extends IoHandlerAdapter {

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		if(null == message)
			return;
		MessageRequest request = (MessageRequest)message;
		AbstructCommand gameCommand = (AbstructCommand) 
				GameServerMain.context.getBean("cmd"+request.getRequestID());
		gameCommand.execute(session, request);
		
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		super.sessionClosed(session);
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("client conn");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
	}

	
}
