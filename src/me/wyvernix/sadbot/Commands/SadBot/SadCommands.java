package me.wyvernix.sadbot.Commands.SadBot;

import me.wyvernix.sadbot.Bots.MasterBot;
import me.wyvernix.sadbot.Commands.BotCommand;


public class SadCommands implements BotCommand {
	
//	public SadCommands() {
//		
//	}

	
	//TODO bonus commands
	public String getCommandName() {
		return "!";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		bot.sendMessage(channel, "lol");
	}

}
