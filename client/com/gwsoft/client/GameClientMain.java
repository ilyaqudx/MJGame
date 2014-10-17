package com.gwsoft.client;

import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.gwsoft.client.codec.IoMessageProtocolFactory;
import com.gwsoft.client.handler.GameClientHandler;

public class GameClientMain {

	public static final String HOST = "127.0.0.1";
	public static final int PORT = 8889;
	public static int userid ;
	public static void main(String[] args,int userid) {
		GameClientMain.userid = userid;
		NioSocketConnector conn = new NioSocketConnector();
		
		IoSessionConfig config = conn.getSessionConfig();
		config.setReadBufferSize(2048);
		config.setIdleTime(IdleStatus.BOTH_IDLE, 15);
		
		DefaultIoFilterChainBuilder filter = conn.getFilterChain();
		filter.addLast("codec", new ProtocolCodecFilter(new IoMessageProtocolFactory()));
		
		conn.setHandler(new GameClientHandler());
		
		InetSocketAddress address = new InetSocketAddress(HOST, PORT);
		ConnectFuture result = conn.connect(address);
		if(result.isConnected())
			System.out.println("连接成功...");
	}
}
