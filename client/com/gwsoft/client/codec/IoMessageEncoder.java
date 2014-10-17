package com.gwsoft.client.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.gwsoft.client.MessageResponse;


public class IoMessageEncoder extends ProtocolEncoderAdapter{

	@Override
	public void encode(IoSession session, Object obj, ProtocolEncoderOutput out)
			throws Exception {
		if(null == obj)
			return;
		MessageResponse response = (MessageResponse)obj;
		System.out.println("·¢ËÍÏûÏ¢±àÂë...");
		System.out.println(response);
		IoBuffer buffer = IoBuffer.allocate(4 + response.getResponseLength());
		
		buffer.putInt(response.getResponseLength());
		buffer.putShort(response.getResponseID());
		buffer.putLong(response.getUid());
		if(response.getBodyLength() > 0)
			buffer.put(response.getBody());
		buffer.flip();
		System.out.println("buffer : "+buffer);
		out.write(buffer);
		System.out.println("send success");
	}
}
