package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

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

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		bot.sendMessage(channel, "Build server: wyv.mcph.co [1.7.4]");
	}

}
