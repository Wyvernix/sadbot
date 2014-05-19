package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class CommandsCommand implements BotCommand {

	public String getCommandName() {
		return "commands";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		bot.sendMessage(channel, "Bot commands: !hello, !ip, !help, !wyvnet, !quote, and some secret ones. Check the wiki.");
	}
	
	public String toString() { return getCommandName(); }

}
