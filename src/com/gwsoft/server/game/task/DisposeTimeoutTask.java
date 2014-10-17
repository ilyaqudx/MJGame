package com.gwsoft.server.game.task;

import java.util.Map;

import com.gwsoft.server.game.GameData;
import com.gwsoft.server.game.GameTable;
import com.gwsoft.server.game.domain.GameUser;

public class DisposeTimeoutTask extends TimeOutTask {

	public DisposeTimeoutTask(GameTable table, GameUser user) {
		super(table, user);
	}

	@Override
	public void doRule() {
		//超时过程中,额外规则
		if(user.isNpc() && table.checkTimeout(begin, GameTable.NPC_RESPONSE_TIME)){
			GameData data = table.getGameData(user.getId());
			Map<Integer, Boolean> dis = data.getCanDispose();
			if(dis.size() > 0){
				if(dis.get(GameData.DISPOSE_PENG))
					data.setDispose(GameData.DISPOSE_PENG);
				else if(dis.get(GameData.DISPOSE_GANG))
					data.setDispose(GameData.DISPOSE_GANG);
				else if(dis.get(GameData.DISPOSE_HU))
					data.setDispose(GameData.DISPOSE_HU);
			}else{
				data.setDispose(GameData.DISPOSE_GUO);
			}
			this.finished();
			table.logic();
		}
	}

}
