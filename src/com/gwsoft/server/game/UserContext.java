package com.gwsoft.server.game;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

import com.gwsoft.server.game.domain.GameUser;

public class UserContext {

	private static UserContext INSTANCE = new UserContext();
	
	public static UserContext getInstance(){
		return INSTANCE;
	}
	private ConcurrentHashMap<Long,GameUser> activeUser = null;
	AtomicInteger count = new AtomicInteger(0);
	public UserContext() {
		activeUser = new ConcurrentHashMap<>();
	}
	
	public void addOnlineUser(GameUser user){
		if(null == user)
			return;
		activeUser.put(user.getId(),user);
	}
	
	public boolean isOnline(long userID){
		return activeUser.get(userID) == null ? false :true;
	}
	
	public GameUser getOnlineUser(long userID){
		return activeUser.get(userID);
	}
	
	public void removeOnlineUser(long userID){
		activeUser.remove(userID);
	}
	
	@Test
	public void test(){
		UserContext.getInstance();
		
	}
}
