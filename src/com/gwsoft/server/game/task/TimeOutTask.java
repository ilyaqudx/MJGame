package com.gwsoft.server.game.task;

import com.gwsoft.server.game.GameTable;
import com.gwsoft.server.game.domain.GameUser;

public abstract class TimeOutTask extends Task {

	public TimeOutTask(GameTable table,GameUser user){
		this.table = table;
		this.user = user;
	}
	@Override
	public void run(){
		begin = System.currentTimeMillis();
		while(!finished){
			if(table.checkTimeout(begin,table.maxTimeout)){ 
				onFinished();
				//发送超时通知
				System.out.println("超时 : "+user.getId()+",name : "+user.getName());
				table.handleTimeout();
			}
			else
				try {
					Thread.sleep(200);
					doRule();
				} catch (Exception e) {
				}
		}
	}
}
