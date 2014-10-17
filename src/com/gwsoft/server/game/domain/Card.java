package com.gwsoft.server.game.domain;

public class Card {

	public static final int TYEE_TONG = 1;
	public static final int TYPE_TIAO = 2;
	public static final int TYPE_WAN  = 3;
	
	public static final int MAX_TYPE = 3;
	public static final int MAX_NUM = 9;
	
	public Card(int type,int num){
		this.type = type;
		this.num = num;
	}
	
	private int type;
	private int num;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public int getCode(){
		return (type-1)*MAX_NUM + num;
	}
	
	@Override
	public String toString() {
		return "Card [type=" + type + ", num=" + num + "]";
	}
	
	private String getLocalString(Card c){
		StringBuffer sb = new StringBuffer();
		sb.append(c.getNum());
		switch (c.getType()) {
		case TYEE_TONG:
			sb.append("Í²");
			break;
		case TYPE_TIAO:
			sb.append("Ìõ");
			break;
		case TYPE_WAN:
			sb.append("Íò");
			break;
		}
		return sb.toString();
	}
	
	public String toLocalString(){
		return getLocalString(this);
	}
}
