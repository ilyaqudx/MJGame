package com.gwsoft.client.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.gwsoft.client.MessageRequest;


public class IoMessageDecoder extends CumulativeProtocolDecoder{

	@Override
	protected boolean doDecode(IoSession session, IoBuffer buffer,
			ProtocolDecoderOutput out) throws Exception {
		
		System.out.println("接收消息解码...");
		
		int remaining = buffer.remaining();
		buffer.mark();
		if(remaining < 4){
			System.out.println("不足4字节...");
			return reset(buffer);
		}
		
		int requestLength = buffer.getInt();
		System.out.println("服务器发送的消息总长度..."+requestLength);
		if(buffer.remaining() < requestLength){
			System.out.println("接收到的消息总长度不足 : "+buffer.remaining());
			return reset(buffer);
		}
		
		short requestID = buffer.getShort();
		byte success = buffer.get();
		byte[] body = new byte[requestLength - 3];
		buffer.get(body);
		
		MessageRequest request = new MessageRequest(requestID,success,body);
		System.out.println("接收到的消息"+request);
		out.write(request);
		return true;
	}

	private boolean reset(IoBuffer buffer) {
		buffer.reset();
		return false;
	}

}
