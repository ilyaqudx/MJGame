package com.gwsoft.server.game;

public class GameResult {

	private boolean hu;
	private int huScore;
	private int gangScore;
	public boolean isHu() {
		return hu;
	}
	public void setHu(boolean hu) {
		this.hu = hu;
	}
	public int getHuScore() {
		return huScore;
	}
	public void setHuScore(int huScore) {
		this.huScore = huScore;
	}
	public int getGangScore() {
		return gangScore;
	}
	public void setGangScore(int gangScore) {
		this.gangScore = gangScore;
	}
	public static int getBanker(){
		return 0;
	}
}
