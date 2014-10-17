package com.gwsoft.server.game;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.mina.core.session.IoSession;

import com.gwsoft.server.game.domain.MessageResponse;
import com.gwsoft.server.game.task.Task;

public final class GameServerManager {

	public static final GameServerManager INSTANCE = new GameServerManager();

	private ExecutorService taskPool = Executors.newCachedThreadPool();
	
	private ConcurrentHashMap<Long, Task> runningTask = new ConcurrentHashMap<>();
	
	public Task getUserRunningTask(long uid){
		return runningTask.get(uid);
	}
	
	public void onFinishedOrCancle(long uid){
		runningTask.remove(uid);
	}
	
	public void executeTask(long uid,Task task){
		System.out.println("Ö´ÐÐÈÎÎñ...");
		taskPool.execute(task);
		runningTask.put(uid, task);
	}
	
	public void send2Client(IoSession session,MessageResponse response){
		if(null == session)
			return;
		session.write(response);
	}
}
