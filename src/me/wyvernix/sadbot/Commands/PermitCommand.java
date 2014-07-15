package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class PermitCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "permit";
	}

	@Override
	public void handleMessage(final MasterBot bot, String channel, String sender, String message) {
		final String[] split = message.split(" ");
		System.out.println("::"+message+"::");
		if (split.length < 1){
			bot.sendMessage(channel, sender + "... try permitting a user");
			return;
		} else {
			bot.permitUser(channel,split[0]);
			return;
		}
	}
	public String toString() { return getCommandName(); }
}
