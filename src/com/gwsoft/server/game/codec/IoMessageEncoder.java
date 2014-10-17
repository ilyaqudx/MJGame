package com.gwsoft.server.game.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.gwsoft.server.game.domain.MessageResponse;

public class IoMessageEncoder extends ProtocolEncoderAdapter{

	@Override
	public void encode(IoSession session, Object obj, ProtocolEncoderOutput out)
			throws Exception {
		if(null == obj)
			return;
		//System.out.println("服务器发送消息编码...");
		MessageResponse response = (MessageResponse)obj;
		IoBuffer buffer = IoBuffer.allocate(4 + response.getResponseLength());
		
		buffer.putInt(response.getResponseLength());
		buffer.putShort(response.getResponseID());
		buffer.put(response.getSuccess());
		if(0 < response.getBodyLength())
			buffer.put(response.getBody());
		buffer.flip();
		//System.out.println("服务器发送消息..."+buffer);
		out.write(buffer);
	}
}
