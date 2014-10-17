package com.gwsoft.server.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.gwsoft.server.game.domain.Card;
import com.gwsoft.server.game.domain.GameUser;
import com.gwsoft.server.game.task.ReadyTimeoutTask;

public class GameTable {

	public int maxTimeout = 2 * 1000;
	public static final int NPC_RESPONSE_TIME = 3*1000;
	public static final int MaxUserCount = 4;
	public static final int TableCardCount = 108;
	public static final int ShuffleCardCount = 5;
	public static final int Status_Null = 0;
	public static final int Status_Ready = 1;
	public static final int Status_PlayCard = 2;
	public static final int Status_OutCardResponse = 3;
	public static final int Status_GameOver = 4;
	public static final int Status_PutOneCard = 5;
	private int _status = Status_Null;
	public void setStatus(int status){
		this._status = status;
	}
	public GameTable(int id){
		this.id = id;
	}
	
	private int id;

	public int getID(){
		return this.id;
	}
	
	
	private ConcurrentHashMap<Long,GameUser> tableUsers = new ConcurrentHashMap<>(MaxUserCount);
	private Map<Long, GameData> gameDatas = new HashMap<>();
	private Map<Long,GameResult> gameResults = new HashMap<>();
	private List<Long> useridOfTableUsers = new ArrayList<>(4);
	private List<Card> tableCards = new ArrayList<>();
	private boolean initCard = false;
	private boolean full = false;
	private boolean allReady = false;
	private int actionSeat = 0;
	private int banker = 0;
	private void nextAction(){
		System.out.println("前一个活动玩家 : "+actionSeat);
		actionSeat = actionSeat < MaxUserCount -1 ? ++actionSeat : 0;
		System.out.println("现在的-----------活动玩家 : "+actionSeat);
	}
	public boolean isFull(){
		return full;
	}
	
	public ConcurrentHashMap<Long,GameUser> getUsers(){
		return this.tableUsers;
	}
	
	private AtomicInteger seatUserCount = new AtomicInteger(0);
	
	public void recordGameResult(Long id,GameResult result){
		gameResults.put(id,result);
	}
	
	public GameResult getGameResult(long id){
		return gameResults.get(id);
	}
	
	public GameData getGameData(long uid){
		return gameDatas.get(uid);
	}
	
	public void registerUserGameData(long uid){
		GameData data = new GameData();
		data.setSeat(tableUsers.size()-1);
		this.gameDatas.put(uid, data);
	}
	
	public void unRegisterUserGameData(long uid){
		this.gameDatas.remove(uid);
	}
	
	public boolean seatDown(GameUser user){
		tableUsers.put(user.getId(), user);
		useridOfTableUsers.add(user.getId());
		user.setTableID(id);
		if(seatUserCount.addAndGet(1) >= MaxUserCount){
			full = true;
		}
		return true;
	}
	
	
	public void logic(){
		System.out.println("table status = "+_status);
		switch (_status) {
		case Status_Ready:
			System.out.println("allReady = "+allReady+",当前人数 : "+seatUserCount.get());
			if(allReady()){
				startGame();
			}
			break;
		case Status_PutOneCard:
			System.out.println("摸牌,当前玩家 : "+actionSeat);
			long uid = useridOfTableUsers.get(actionSeat);
			GameData data = gameDatas.get(uid);
			int count = tableCards.size();
			if(0 == count){
				System.out.println("牌摸完了,进入结算状态");
				setStatus(Status_GameOver);
			}else{
				Card c = tableCards.remove(0);
				System.out.println("摸的牌 : "+c.toLocalString()+",桌子上剩余牌数量 : "+tableCards.size());
				data.getHandCard().add(c);
				nextStatus(false,false);
				GameServerManager.INSTANCE.executeTask(uid, new ReadyTimeoutTask(this, tableUsers.get(uid)));
			}
			break;
		case Status_PlayCard:
			break;
		case Status_OutCardResponse:
			//Card outCard = gameDatas.get(useridOfTableUsers.get(actionSeat)).getPrepareOutCard();
			int[] disposes = new int[4];
			for (GameData d : gameDatas.values()) {
				if(d.canDispose.size() != 0 && d.getDispose() == GameData.DISPOSE_NULL)
					return;
				else{
					disposes[d.getSeat()] = d.getDispose();
				}
			}
			
			Arrays.sort(disposes);
			int disposeResult = disposes[0];
			if(disposeResult == GameData.DISPOSE_HU){
				
			}else if(disposeResult == GameData.DISPOSE_GANG){
				
			}else if(disposeResult == GameData.DISPOSE_PENG){
				
			}else{
				nextStatus(true, true);
			}
			break;
		case Status_GameOver:
			break;
		}
	}
	
	public static boolean isSame(Card c1,Card c2){
		return c1.getType() == c2.getType() && c1.getNum() == c2.getNum();
	}
	
	private boolean allReady() {
		GameData temp = null;
		if(full){
			for (Long id : gameDatas.keySet()) {
				temp = gameDatas.get(id);
				if(null == temp)
					return false;
				if(!temp.isReady())
					return false;
			}
			return true;
		}
		
		return false;
	}
	/**Begin game*/
	private void startGame() {
		System.out.println("start game ... ");
		if(!initCard)
			createCard();
		shuffleCard();
		putCard();
		banker = getBanker();
		actionSeat = banker;
		nextStatus(false,true);
	}
	
	private Random random = new Random();
	
	public int getBanker(){
		return random.nextInt(3);
	}
	
	private void createCard(){
		System.out.println("初始化卡牌.........");
		Card c = null;
		for (int i = 1; i <= 3; i++) {
			for (int j = 0; j < 4; j++) {
				for (int j2 = 1; j2 <= 9; j2++) {
					c = new Card(i,j2);
					tableCards.add(c);
				}
			}
		}
	}
	
	public void sort(List<Card> cards)throws NullPointerException{
		if(null == cards)throw new NullPointerException("cards is null");
		int count = cards.size();
		Card c1 = null;
		Card c2 = null;
		boolean same = false;
		for (int i = 0; i < count -1 ; i++) {
			c1 = cards.get(i);
			for (int j = i+1; j < count - i -1; j++) {
				c2 = cards.get(j);
				same = c1.getType() - c2.getType() == 0;
				if(same){
					if(c1.getNum() > c2.getNum()){
						swap(c1, c2);
					}
				}else{
					if(c1.getType() > c2.getType()){
						swap(c1, c2);
					}
				}
			}
		}
	}
	private void swap(Card c1, Card c2) {
		Card temp;
		temp = c1;
		c1 = c2;
		c2 = temp;
	}
	
	private void shuffleCard(){
		System.out.println("洗牌--------------");
		for (int i = 0; i < ShuffleCardCount; i++) {
			Collections.shuffle(tableCards);
		}
	}
	
	private void putCard(){
		System.out.println("发牌-----------------");
		GameData data = null;
		for (GameUser user : tableUsers.values()) {
			data = gameDatas.get(user.getId());
			for (int i = 0; i < 13; i++) {
				data.getHandCard().add(tableCards.remove(0));
			}
			sort(data.getHandCard());
		}
		System.out.println("发完牌之后,余下牌数量 : "+tableCards.size());
	}
	
	private int getNextStatus(){
		switch (_status) {
		case Status_Ready:
			return Status_PutOneCard;
		case Status_PutOneCard:
			return Status_PlayCard;
		case Status_PlayCard:
			return Status_OutCardResponse;
		case Status_OutCardResponse:
			return Status_PutOneCard;
		}
		return Status_Null;
	}
	
	
	Map<Integer,Map<Integer, Boolean>> userCanDispose = new HashMap<>();
	public void trusteeshipAI(){
		switch (_status) {
		case Status_PlayCard:
			GameData data = gameDatas.get(useridOfTableUsers.get(actionSeat));
			List<Card> handCard = data.getHandCard();
			Card c = handCard.remove(0);
			data.setPrepareOutCard(c);
			data.calCanResponseCard();
			System.out.println("托管自动出牌,card : "+c.toLocalString()+",余牌数量 = "+handCard.size());
			
			//看是否能有人能碰杠
			Map<Integer, Boolean> tempDispose = null;
			for (GameData d : gameDatas.values()) {
				if(d.getSeat() == data.getSeat())
					continue;
				tempDispose = d.canDisposeCard(c);
				if(tempDispose.size() > 0)
					userCanDispose.put(d.getSeat(), tempDispose);
			}
			
			if(userCanDispose.size() > 0){
				nextStatus(false, false);
			}else{
				nextStatus(Status_PutOneCard, true, true);
			}
			break;
		case Status_OutCardResponse:
			break;
		}
		nextStatus(false,true);
	}
	
	public void nextStatus(int nextStatus,boolean nextActionSeat,boolean executeLogic){
		setStatus(nextStatus);
		if(nextActionSeat)
			nextAction();
		if(executeLogic)
			logic();
	}
	
	//下一个状态
	public void nextStatus(boolean nextActionSeat,boolean executeLogic) {
		nextStatus(getNextStatus(), nextActionSeat, executeLogic);
	}
	public void handleTimeout() {
		trusteeshipAI();
	}
	/**Begin game over*/
	
	public boolean checkTimeout(long begin,long maxTimeout){
		return System.currentTimeMillis() - begin >= maxTimeout;
	}
	
}
