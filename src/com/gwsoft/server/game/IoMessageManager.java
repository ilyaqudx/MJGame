package com.gwsoft.server.game;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.gwsoft.server.game.domain.MessageResponse;

public class IoMessageManager {

	private static IoMessageManager instance = new IoMessageManager();
	
	private BlockingQueue<MessageResponse> resQueue =
			new ArrayBlockingQueue<>(50);
	
	public static IoMessageManager getInstance(){
		return instance;
	}
	
	
}
