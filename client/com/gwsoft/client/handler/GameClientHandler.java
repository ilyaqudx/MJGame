package com.gwsoft.client.handler;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.gwsoft.client.GameClientMain;
import com.gwsoft.client.MessageDispatcher;
import com.gwsoft.client.MessageRequest;
import com.gwsoft.client.MessageResponse;
import com.gwsoft.server.game.protobuf.UserProtobuf.User;
import com.gwsoft.server.game.protobuf.UserProtobuf.User.Builder;
import com.gwsoft.server.game.utils.ReqIDConstant;

public class GameClientHandler implements IoHandler {

	@Override
	public void exceptionCaught(IoSession arg0, Throwable arg1)
			throws Exception {

	}

	@Override
	public void messageReceived(IoSession session, Object obj) throws Exception {
		if(null == obj)
			return;
		
		try {
			System.out.println("obj = "+obj);
			MessageRequest request = (MessageRequest)obj;
			MessageDispatcher.INSTANCE.dispatch(request, session);
			System.out.println("request : "+request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void messageSent(IoSession arg0, Object arg1) throws Exception {

	}

	@Override
	public void sessionClosed(IoSession arg0) throws Exception {

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		Builder builder = User.newBuilder();
		builder.setName("张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三" +
				"张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三" +
				"三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三张三");
		builder.setAge(20);
		builder.setEmail("abc@qq.com");
		builder.setId(GameClientMain.userid);
		builder.setSex(1);
		MessageResponse response = new MessageResponse((short)ReqIDConstant.Login,builder.getId(),builder.build().toByteArray());

		session.write(response);
		System.out.println("客户端发送消息");
	}

	@Override
	public void sessionIdle(IoSession arg0, IdleStatus arg1) throws Exception {

	}

	@Override
	public void sessionOpened(IoSession arg0) throws Exception {

	}

}
