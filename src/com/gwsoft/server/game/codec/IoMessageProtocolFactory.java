package com.gwsoft.server.game.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class IoMessageProtocolFactory implements ProtocolCodecFactory{

	public static final String ENCODE = "UTF-8";
	
	
	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return new IoMessageDecoder();
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return new IoMessageEncoder();
	}

}
