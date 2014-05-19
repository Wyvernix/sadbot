package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class HelloCommand implements BotCommand {

	public String getCommandName() {
		return "hello";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		bot.sendMessage(channel, "Welcome to the channel! Follow or die! R)");
	}
	public String toString() { return getCommandName(); }
}
