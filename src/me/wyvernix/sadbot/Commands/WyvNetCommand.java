package me.wyvernix.sadbot.Commands;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

/**
 * A simple time command. Tells the bot to give
 * the current time when it is given "time".
 *
 * @author AMcBain ( http://www.asmcbain.net/ ) @ 2009
 */
public class WyvNetCommand implements BotCommand {

	public String getCommandName() {
		return "wyvnet";
	}

	public void handleMessage(PircBot bot, String channel, String sender, String message, ArrayList<String> mods) {
		bot.sendMessage(channel, "Build server: wyv.mcph.co [1.7.4]");
	}

}
