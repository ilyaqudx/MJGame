package com.gwsoft.server.game.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.gwsoft.server.game.domain.MessageRequest;

public class IoMessageDecoder extends CumulativeProtocolDecoder{

	@Override
	protected boolean doDecode(IoSession session, IoBuffer buffer,
			ProtocolDecoderOutput out) throws Exception {
		
		//System.out.println("������Ϣ����...");
		buffer.setAutoExpand(true);
		int remaining = buffer.remaining();
		//System.out.println("��ǰ���յ������ݳ��� = "+remaining);
		//System.out.println("buffer begin : "+buffer);
		buffer.mark();
		try {
			if(remaining < 4){
				//System.out.println("��ǰ���յ������ݳ��Ȳ���4�ֽ�");
				return reset(buffer);
			}
			
			int requestLength = buffer.getInt();
			//System.out.println("��ǰ���յ�����Ϣ���峤�� = "+requestLength);
			
			if(buffer.remaining() < requestLength){
				//System.out.println("��ǰ�����������ֽ�,������Ϣ�ܳ���");
				//System.out.println("buffer middle : "+buffer);
				return reset(buffer);
			}
			
			short requestID = buffer.getShort();
			long uid = buffer.getLong();
			byte[] body = new byte[requestLength - 10];
			buffer.get(body);
			
			MessageRequest request = new MessageRequest(requestID,uid,body);
			//System.out.println("request = "+request);
			out.write(request);
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			reset(buffer);
		}
		return false;
	}

	private boolean reset(IoBuffer buffer) {
		buffer.reset();
		//System.out.println("buffer end : "+buffer);
		return false;
	}

}
