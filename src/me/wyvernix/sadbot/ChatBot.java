package me.wyvernix.sadbot;

import java.util.ConcurrentModificationException;

import com.google.code.chatterbotapi.*;

public class ChatBot {
	private ChatterBotSession clever;
	private ChatterBot bot1;
	
//	private Timer timer;
	
	public ChatBot() throws Exception {
        bot1 = new ChatterBotFactory().create(ChatterBotType.CLEVERBOT);
    }
	
	public void newSession() {
		clever = bot1.createSession();
	}
	
	private static boolean isRunning = false;
//	private static int tries = 0;
	
	public String getResponse(final String seed) throws Exception {
		if (!isRunning) {
			isRunning = true;
//			tries = 0;
			final String response = clever.think(seed);
			isRunning = false;
			return response;
			
		} else {
//			tries++;
//			if (tries > 0) {
//				
//			}
			throw new ConcurrentModificationException();
		}
	}
}
