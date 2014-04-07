package me.wyvernix.sadbot.Commands.SadBot;

import java.util.ArrayList;

import me.wyvernix.sadbot.Commands.BotCommand;

import org.jibble.pircbot.PircBot;


public class SadCommands implements BotCommand {
	
//	public SadCommands() {
//		
//	}

	public String getCommandName() {
		return "!";
	}

	public void handleMessage(PircBot bot, String channel, String sender, String message, ArrayList<String> mods) {
		bot.sendMessage(channel, "lol");
	}

}
