package com.gwsoft.server.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gwsoft.server.game.domain.Card;

public class GameData {
	
	public static final int DISPOSE_PENG = 3;
	public static final int DISPOSE_GANG = 2;
	public static final int DISPOSE_HU = 1;
	public static final int DISPOSE_GUO = -1;
	public static final int DISPOSE_NULL = 0;
	
	
	
	private boolean canPeng;
	private boolean canGang;
	private boolean canHu;
	public boolean isCanPeng() {
		return canPeng;
	}

	public void setCanPeng(boolean canPeng) {
		this.canPeng = canPeng;
	}

	public boolean isCanGang() {
		return canGang;
	}

	public void setCanGang(boolean canGang) {
		this.canGang = canGang;
	}

	public boolean isCanHu() {
		return canHu;
	}

	public void setCanHu(boolean canHu) {
		this.canHu = canHu;
	}


	private int dispose;
	
	public void setDispose(int dispose){
		this.dispose  = dispose;
	}
	
	public int getDispose(){
		return this.dispose;
	}
	
	
	private int seat = -1;
	private boolean timeout = false;
	public boolean isTimeout() {
		return timeout;
	}
	public void setTimeout(boolean timeout) {
		this.timeout = timeout;
	}
	private boolean ready = false;
	private Card putCard;
	private Card prepareOutCard;
	private List<List<Card>> pengOrGangCard = new ArrayList<List<Card>>();
	private List<Card> outCardGroup = new ArrayList<>();
	private List<Card> handCard = new ArrayList<>();
	
	private List<Card> canPengGroup = new ArrayList<>();
	private List<Card> canGangGroup = new ArrayList<>();
	private List<Card> canHuGroup = new ArrayList<>();
	
	Map<Integer, Boolean> canDispose = new HashMap<>();
	
	public Map<Integer, Boolean> getCanDispose() {
		return canDispose;
	}

	public Map<Integer, Boolean> canDisposeCard(Card out){
		canDispose.clear();
		boolean result = canPeng(out);
		if(result)
			canDispose.put(DISPOSE_PENG, true);
		result = canGang(out);
		if(result)
			canDispose.put(DISPOSE_GANG, true);
		result = canHu(out);
		if(result)
			canDispose.put(DISPOSE_HU, true);
		setCanPeng(canPeng(out));
		setCanGang(canGang(out));
		setCanHu(canHu(out));
		return canDispose;
	}
	
	//计算能碰/杠/胡的牌.
	public void calCanResponseCard(){
		int[][] temp = new int[27][1];
		int count = handCard.size();
		Card c = null;
		for (int i = 0; i < count; i++) {
			c = handCard.get(i);
			temp[c.getCode() - 1][0]++;
		}
		
		//计算结果
		int result = 0;
		for (int i = 0; i < temp.length; i++) {
			result = temp[i][0];
			c = new Card(((i+1)/9 + 1),(i+1)%9);
			if(result == 3){
				canGangGroup.add(c);
				canPengGroup.add(c);
			}else if(result == 2){
				canPengGroup.add(c);
			}
		}
	}
	
	
	public boolean canHu(Card c){
		for (Card hu : canHuGroup) {
			if(GameTable.isSame(c, hu))
				return true;
		}
		return false;
	}
	
	public boolean canGang(Card c){
		for (Card gang : canGangGroup) {
			if(GameTable.isSame(c, gang))
				return true;
		}
		return false;
	}
	
	public boolean canPeng(Card c){
		for (Card peng : canPengGroup) {
			if(GameTable.isSame(c, peng))
				return true;
		}
		return false;
	}
	
	public List<List<Card>> getPengOrGangCard() {
		return pengOrGangCard;
	}
	public void setPengOrGangCard(List<List<Card>> pengOrGangCard) {
		this.pengOrGangCard = pengOrGangCard;
	}
	public List<Card> getOutCardGroup() {
		return outCardGroup;
	}
	public void setOutCardGroup(List<Card> outCardGroup) {
		this.outCardGroup = outCardGroup;
	}
	public List<Card> getHandCard() {
		return handCard;
	}
	public void setHandCard(List<Card> handCard) {
		this.handCard = handCard;
	}
	public void clear(){
		seat = -1;
		ready = false;
		timeout = false;
		putCard = null;
		prepareOutCard = null;
		pengOrGangCard.clear();
		outCardGroup.clear();
	}
	
	public int getSeat() {
		return seat;
	}
	public void setSeat(int seat) {
		this.seat = seat;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	public Card getPutCard() {
		return putCard;
	}
	public void setPutCard(Card putCard) {
		this.putCard = putCard;
	}
	public Card getPrepareOutCard() {
		return prepareOutCard;
	}
	public void setPrepareOutCard(Card prepareOutCard) {
		this.prepareOutCard = prepareOutCard;
	}
	
	public void addPengOrGangGroup(List<Card> c){
		if(null == c)return;
		pengOrGangCard.add(c);
	}
	
	public List<List<Card>> getPengOrGangGroup(){
		return pengOrGangCard;
	}
	
	public void addHistoryOutCard(Card c){
		if(null == c)return;
		outCardGroup.add(c);
	}
	
}
