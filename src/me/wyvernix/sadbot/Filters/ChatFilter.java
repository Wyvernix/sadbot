package me.wyvernix.sadbot.Filters;

import java.util.ArrayList;

import me.wyvernix.sadbot.Bots.MasterBot;

public interface ChatFilter {
	// The method where each BotCommand implementor will handle the event
	public String handleMessage(MasterBot bot, String channel, String sender, String message, ArrayList<String> mods, ArrayList<String> special);
	
	@Override
	public String toString();
}
