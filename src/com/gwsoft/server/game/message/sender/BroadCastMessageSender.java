package com.gwsoft.server.game.message.sender;

import java.util.List;

import com.gwsoft.server.game.GameData;
import com.gwsoft.server.game.GameServerManager;
import com.gwsoft.server.game.GameTable;
import com.gwsoft.server.game.domain.GameUser;
import com.gwsoft.server.game.domain.MessageResponse;
import com.gwsoft.server.game.protobuf.ReadyProtobuf;
import com.gwsoft.server.game.protobuf.ReadyProtobuf.Ready;
import com.gwsoft.server.game.protobuf.UserCardsProtobuf.Card;
import com.gwsoft.server.game.protobuf.UserCardsProtobuf.UserCards;
import com.gwsoft.server.game.utils.ResIDConstant;

public class BroadCastMessageSender implements MessageSender{

	public static void broadcastUserReady(GameTable table,GameUser user,boolean ready){
		if(null == user)
			return;
		MessageResponse response = null;
		ReadyProtobuf.Ready.Builder builder = Ready.newBuilder();
		for (GameUser u : table.getUsers().values()) {
			if(user == u)
				continue;
			builder.setReady(ready?SUCCESS:FAIL);
			response = new MessageResponse(ResIDConstant.BROADCAST_READY,SUCCESS,builder.build().toByteArray());
			GameServerManager.INSTANCE.send2Client(u.getSession(), response);
		}
	}
	
	public static void broadcastStartGame(GameTable table){
		GameData data = null;
		int count ;
		com.gwsoft.server.game.protobuf.UserCardsProtobuf.UserCards.Builder builder =  null;
		List<com.gwsoft.server.game.domain.Card> handCard;
		MessageResponse response = null;
		for (GameUser u : table.getUsers().values()) {
			builder = UserCards.newBuilder();
			data = table.getGameData(u.getId());
			handCard = data.getHandCard();
			count = data.getHandCard().size();
			for (int i = 0; i < count; i++) {
				builder.addCard(Card.newBuilder().setType(handCard.get(i).getType()).
											setNum(handCard.get(i).getNum()));
			}
			response = new MessageResponse(ResIDConstant.BROADCAST_STARTGAME, 
					SUCCESS, builder.build().toByteArray());
			GameServerManager.INSTANCE.send2Client(u.getSession(), response);
		}
	}
}
