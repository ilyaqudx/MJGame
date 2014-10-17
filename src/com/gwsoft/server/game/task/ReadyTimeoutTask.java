package com.gwsoft.server.game.task;

import com.gwsoft.server.game.GameTable;
import com.gwsoft.server.game.domain.GameUser;

public class ReadyTimeoutTask extends TimeOutTask {

	public ReadyTimeoutTask(GameTable table, GameUser user) {
		super(table, user);
	}

	@Override
	public void doRule() {
		System.out.println("无额外的规则执行...");
	}

}
