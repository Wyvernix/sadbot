package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

/**
 * A simple time command. Tells the bot to give
 * the current time when it is given "time".
 *
 * @author AMcBain ( http://www.asmcbain.net/ ) @ 2009
 */
public class HelloCommand implements BotCommand {

	public String getCommandName() {
		return "hello";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		bot.sendMessage(channel, "Welcome to the channel! Follow or die! R)");
	}

}
