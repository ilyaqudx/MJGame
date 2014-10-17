package com.gwsoft.server.game;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gwsoft.server.game.codec.IoMessageProtocolFactory;
import com.gwsoft.server.game.handler.GameHandler;

public class GameServerMain {

	public static ApplicationContext context = null;
	public static final int PORT = 8889;
	static{
		context = new ClassPathXmlApplicationContext("application.xml");
	}
	
	public static void main(String[] args) throws Exception {
		
		NioSocketAcceptor server = new NioSocketAcceptor();
		SocketSessionConfig config = server.getSessionConfig();
		config.setReadBufferSize(2048);
		config.setSendBufferSize(2048);
		config.setIdleTime(IdleStatus.BOTH_IDLE, 15);
		
		DefaultIoFilterChainBuilder filter = server.getFilterChain();
		filter.addLast("codec", new ProtocolCodecFilter(new IoMessageProtocolFactory()));
		
		server.setHandler(new GameHandler());
		SocketAddress address = new InetSocketAddress(PORT);
		server.bind(address);
	}
}
