package me.wyvernix.sadbot.Commands;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

public class JoinCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "join";
	}

	@Override
	public void handleMessage(PircBot bot, String channel, String sender, String message, ArrayList<String> mods) {
		if (sender.equals("shady1765") || sender.equals("activeenergylive")){
			final String[] mesBlock = message.split(" ");
			bot.joinChannel("#" + mesBlock[0].toLowerCase());
			bot.sendMessage(channel, "Joining " + mesBlock[0]+"'s channel.");
		}
	}

}
