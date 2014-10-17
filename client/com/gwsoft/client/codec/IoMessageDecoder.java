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
		
		System.out.println("������Ϣ����...");
		
		int remaining = buffer.remaining();
		buffer.mark();
		if(remaining < 4){
			System.out.println("����4�ֽ�...");
			return reset(buffer);
		}
		
		int requestLength = buffer.getInt();
		System.out.println("���������͵���Ϣ�ܳ���..."+requestLength);
		if(buffer.remaining() < requestLength){
			System.out.println("���յ�����Ϣ�ܳ��Ȳ��� : "+buffer.remaining());
			return reset(buffer);
		}
		
		short requestID = buffer.getShort();
		byte success = buffer.get();
		byte[] body = new byte[requestLength - 3];
		buffer.get(body);
		
		MessageRequest request = new MessageRequest(requestID,success,body);
		System.out.println("���յ�����Ϣ"+request);
		out.write(request);
		return true;
	}

	private boolean reset(IoBuffer buffer) {
		buffer.reset();
		return false;
	}

}
