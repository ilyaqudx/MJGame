package com.gwsoft.server.game.task;

import com.gwsoft.server.game.GameServerManager;
import com.gwsoft.server.game.GameTable;
import com.gwsoft.server.game.domain.GameUser;



public abstract class Task implements Runnable{

	protected GameTable table;
	protected int taskID;
	protected boolean finished = false;
	protected long begin;
	protected GameUser user;
	
	
	public int getTaskID() {
		return taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}
	
	public void cancle(){
		onFinished();
	}
	
	protected void onFinished(){
		finished = true;
		GameServerManager.INSTANCE.onFinishedOrCancle(user.getId());
	}
	
	public boolean finished(){
		return finished;
	}
	
	public abstract void doRule();
}
