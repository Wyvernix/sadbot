package me.wyvernix.sadbot;

import java.util.ConcurrentModificationException;
import java.util.Timer;

import com.google.code.chatterbotapi.*;

public class ChatBot {
	ChatterBotSession clever;
	ChatterBot bot1;
	
	Timer timer;
	
	public ChatBot() throws Exception {
        bot1 = new ChatterBotFactory().create(ChatterBotType.CLEVERBOT);
    }
	
	public void newSession() {
		clever = bot1.createSession();
	}
	
	static boolean isRunning = false;
	static int tries = 0;
	
	public String getResponse(final String seed) throws Exception {
		if (!isRunning) {
			isRunning = true;
			tries = 0;
			String response = clever.think(seed);
			isRunning = false;
			return response;
			
		} else {
			tries++;
			if (tries > 0) {
				
			}
			throw new ConcurrentModificationException();
		}
	}
}
