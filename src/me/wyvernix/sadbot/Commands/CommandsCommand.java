package me.wyvernix.sadbot.Commands;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

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

	public void handleMessage(PircBot bot, String channel, String sender, String message, ArrayList<String> mods) {
		bot.sendMessage(channel, "Bot commands: !hello, !ip, !help, !wyvnet, !quote, !command, and some secret ones.");
	}

}
