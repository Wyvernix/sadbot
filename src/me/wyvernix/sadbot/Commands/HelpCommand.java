package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class HelpCommand implements BotCommand {

	public String getCommandName() {
		return "help";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		bot.sendMessage(channel, "lurk more.");
	}
	public String toString() { return getCommandName(); }
}
