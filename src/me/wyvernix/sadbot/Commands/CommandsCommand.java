package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

/**
 * A simple time command. Tells the bot to give
 * the current time when it is given "time".
 *
 * @author AMcBain ( http://www.asmcbain.net/ ) @ 2009
 */
public class CommandsCommand implements BotCommand {

	public String getCommandName() {
		return "commands";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		bot.sendMessage(channel, "Bot commands: !hello, !ip, !help, !wyvnet, !quote, and some secret ones.");
	}
	
	public String toString() { return getCommandName(); }

}
