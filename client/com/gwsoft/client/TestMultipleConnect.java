package com.gwsoft.client;

public class TestMultipleConnect {

	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 4; i++) {
			GameClientMain.main(null,100+i*10);
			Thread.sleep(3000);
		}
	}
}
