package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class WyvNetCommand implements BotCommand {

	public String getCommandName() {
		return "wyvnet";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		bot.sendMessage(channel, "Build server: wyv.mcph.co [1.7.4]");
	}
	public String toString() { return getCommandName(); }
}
