package me.wyvernix.sadbot.Commands;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

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

	public void handleMessage(PircBot bot, String channel, String sender, String message, ArrayList<String> mods) {
		bot.sendMessage(channel, "Welcome to the channel! Follow or die! R)");
	}

}
