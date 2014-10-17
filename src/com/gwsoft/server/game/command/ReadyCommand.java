package com.gwsoft.server.game.command;

import org.apache.mina.core.session.IoSession;

import com.google.protobuf.InvalidProtocolBufferException;
import com.gwsoft.server.game.GameData;
import com.gwsoft.server.game.GameRoom;
import com.gwsoft.server.game.GameServerManager;
import com.gwsoft.server.game.GameTable;
import com.gwsoft.server.game.UserContext;
import com.gwsoft.server.game.domain.GameUser;
import com.gwsoft.server.game.domain.MessageRequest;
import com.gwsoft.server.game.message.sender.ResponseMessageSender;
import com.gwsoft.server.game.protobuf.ReadyProtobuf.Ready;
import com.gwsoft.server.game.task.Task;
import com.gwsoft.server.game.task.TimeOutTask;

public class ReadyCommand extends AbstructCommand {

	@Override
	public void execute(IoSession session, MessageRequest request){
		long uid = request.getUid();
		GameUser user = UserContext.getInstance().getOnlineUser(uid);
		GameTable t = GameRoom.INSTANCE.getTableOfUserInRoom(user.getTableID());
		if(null == t)
			return;
		//获取超时任务
		Task task = GameServerManager.INSTANCE.getUserRunningTask(uid);
		if(task instanceof TimeOutTask){
			if(task.finished()){
				//准备失败
				System.out.println("准备失败,已经超时...");
				return;
			}else{
				System.out.println("未超时...");
				task.cancle();
			}
		}
		try {
			Ready ready = Ready.parseFrom(request.getBody());
			GameData data = t.getGameData(uid);
			System.out.println("data = "+data);
			data.setReady(ready.getReady() == 1?true:false);
			System.out.println("uid : "+uid+"ready : "+ready.getReady());
			t.logic();
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
}
