package com.gwsoft.server.game.command;

import org.apache.mina.core.session.IoSession;

import com.gwsoft.server.game.GameRoom;
import com.gwsoft.server.game.GameServerManager;
import com.gwsoft.server.game.GameTable;
import com.gwsoft.server.game.UserContext;
import com.gwsoft.server.game.domain.GameUser;
import com.gwsoft.server.game.domain.MessageRequest;
import com.gwsoft.server.game.message.sender.ResponseMessageSender;
import com.gwsoft.server.game.task.ReadyTimeoutTask;

public class EnterRoomCommand extends AbstructCommand {

	@Override
	public void execute(IoSession session, MessageRequest request) {
		long uid = request.getUid();
		System.out.println("enterroom uid = "+request.getUid());
		GameUser user = UserContext.getInstance().getOnlineUser(uid);
		GameTable table = GameRoom.INSTANCE.getTableOfUserInRoom(user.getTableID());
		if(null != table){
			//说明之前已经在房间
		}else{
			boolean success = GameRoom.INSTANCE.seatDown2Table(user);
			table = GameRoom.INSTANCE.getTableOfUserInRoom(user.getTableID());
			if(success && null != table){
				table.registerUserGameData(uid);//给用户添加房间数据
				GameServerManager.INSTANCE.executeTask(uid,
						new ReadyTimeoutTask(GameRoom.INSTANCE.getTableOfUserInRoom(user.getTableID()),user));
				ResponseMessageSender.resposneEnterRoom(user, true);
			}else{
				ResponseMessageSender.resposneEnterRoom(user, false);
			}
		}
	}

}
