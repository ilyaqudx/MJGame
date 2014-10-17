package com.gwsoft.server.game.domain;

import org.apache.mina.core.session.IoSession;

public class GameUser {

	private long id;
	private String name;
	private int sex;
	private int age;
	private String tel;
	private boolean npc;
	
	public boolean isNpc() {
		return npc;
	}
	public void setNpc(boolean npc) {
		this.npc = npc;
	}
	private IoSession session;
	
	private long roomID;
	private int tableID;
	
	public long getRoomID() {
		return roomID;
	}
	public void setRoomID(long roomID) {
		this.roomID = roomID;
	}
	public int getTableID() {
		return tableID;
	}
	public void setTableID(int tableID) {
		this.tableID = tableID;
	}
	private boolean isOnline;
	
	public void setOffLine(){
		setOnline(false);
	}
	public boolean isOnline() {
		return isOnline;
	}
	public void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
	}
	@Override
	public String toString() {
		return "GameUser [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", age=" + age + ", tel=" + tel + ", session=" + session
				+ "]";
	}
}
