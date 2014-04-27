package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class JoinCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "join";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		if (sender.equals("shady1765") || sender.equals("activeenergylive")){
			final String[] mesBlock = message.split(" ");
			bot.joinChannel("#" + mesBlock[0].toLowerCase());
			bot.sendMessage(channel, "Joining " + mesBlock[0]+"'s channel.");
		}
	}
	public String toString() { return getCommandName(); }
}
