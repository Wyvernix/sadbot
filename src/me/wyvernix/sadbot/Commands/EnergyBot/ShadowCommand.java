package me.wyvernix.sadbot.Commands.EnergyBot;

import java.util.ArrayList;

import me.wyvernix.sadbot.Commands.BotCommand;

import org.jibble.pircbot.PircBot;

/**
 * A simple time command. Tells the bot to give
 * the current time when it is given "time".
 *
 * @author AMcBain ( http://www.asmcbain.net/ ) @ 2009
 */
public class ShadowCommand implements BotCommand {

	public String getCommandName() {
		return "shadow";
	}

	public void handleMessage(PircBot bot, String channel, String sender, String message, ArrayList<String> mods) {
		bot.sendMessage(channel, "FrankerZ FrankerZ FrankerZ");
	}

}
