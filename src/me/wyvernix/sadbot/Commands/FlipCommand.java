package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class FlipCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "flip";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender,
			String message) {
		if (message.length() < 1) {
			bot.sendMessage(channel, "(ノ｀Д´)ノ彡 "+sender);
			return;
		}
		bot.sendMessage(channel, "(ノ｀Д´)ノ彡 "+message);
	}
	
	public String toString() { return getCommandName(); }
}