package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class ViewersCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "viewers";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender,
			String message) {
		bot.sendMessage(channel, "There are "+(bot.getActiveUsers().size()-1) + " viewers in chat.");

	}
	
	@Override
	public String toString() { return getCommandName(); }
}