package me.wyvernix.sadbot.Commands.RPG;

import java.util.ArrayList;

import me.wyvernix.sadbot.Commands.BotCommand;

import org.jibble.pircbot.PircBot;

public class RPGCommand implements BotCommand {

	public String getCommandName() {
		return "rpg";
	}

	public void handleMessage(PircBot bot, String channel, String sender, String message, ArrayList<String> mods) {
		if ((message.length() < "rpg ".length()) || (message.startsWith("rpg help"))) {
			bot.sendMessage(channel, "Do '!rpg commands' for commands");
//		} else if (more name stuffs) {
			
		} else {
			bot.sendMessage(channel, "Do '!rpg help'");
		}
	}

}
