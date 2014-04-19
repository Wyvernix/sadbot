package me.wyvernix.sadbot.Commands.RPG;

import me.wyvernix.sadbot.Bots.MasterBot;
import me.wyvernix.sadbot.Commands.BotCommand;

public class RPGCommand implements BotCommand {

	public String getCommandName() {
		return "rpg";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		if ((message.length() < "rpg ".length()) || (message.startsWith("rpg help"))) {
			bot.sendMessage(channel, "Do '!rpg commands' for commands");
//		} else if (more name stuffs) {
			
		} else {
			bot.sendMessage(channel, "Do '!rpg help'");
		}
	}

}
