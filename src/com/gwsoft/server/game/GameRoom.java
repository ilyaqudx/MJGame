package com.gwsoft.server.game;

import java.util.concurrent.ConcurrentHashMap;

import com.gwsoft.server.game.domain.GameUser;

public class GameRoom {
	
	private long id;
	private String name;
	
	public static final GameRoom INSTANCE = new GameRoom();
	public static final int TableCount = 100;
	ConcurrentHashMap<Integer,GameTable> tables = new ConcurrentHashMap<>(); 
	ConcurrentHashMap<Integer, GameTable> activeTables = new ConcurrentHashMap<>();
	
	private GameRoom(){
		for (int i = 1; i <= 100; i++) {
			tables.put(i, new GameTable(i));
		}
	}
	
	public boolean seatDown2Table(GameUser user){
		GameTable table = getOneEmptyTable();
		if(null == table)
			return false;
		return table.seatDown(user);
	}
	
	private GameTable getOneEmptyTable(){
		for (GameTable table : activeTables.values()) {
			if(!table.isFull())
				return table;
		}
		GameTable t = null;
		for (Integer key : tables.keySet()) {
			t = tables.remove(key);
			activeTables.put(key, t);
			t.setStatus(GameTable.Status_Ready);
			return t;
		}
		
		return null;
	}
	
	public GameTable getTableOfUserInRoom(int tableID){
		return activeTables.get(tableID);
	}
	
}
