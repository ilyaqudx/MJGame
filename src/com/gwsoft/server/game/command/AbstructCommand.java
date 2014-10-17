package com.gwsoft.server.game.command;

import org.apache.mina.core.session.IoSession;

import com.gwsoft.server.game.domain.MessageRequest;
import com.gwsoft.server.game.utils.ReqIDConstant;
public abstract class AbstructCommand {

	public static final String USER_ID = "userid";
	
	public void doCommand(IoSession session,MessageRequest request){
		if(null == session)return;
		short requestID = request.getRequestID();
		boolean Success = true;
		if(ReqIDConstant.Login != requestID)
			Success = checkLogin(session, request.getUid());
		if(Success)
			execute(session, request);
		else
			System.out.println("unknow user");
	}
	
	private boolean checkLogin(IoSession session,long uid){
		Object obj = session.getAttribute(uid);
		if(null == obj)
			return false;
		return true;
	}
	
	public abstract void execute(IoSession session,MessageRequest request);
}
