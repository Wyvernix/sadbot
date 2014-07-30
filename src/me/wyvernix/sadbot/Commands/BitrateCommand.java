package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.GSONic;
import me.wyvernix.sadbot.Bots.MasterBot;

public class BitrateCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "bitrate";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		String bitrate = GSONic.getBitrate(channel.replace("#", ""));
		
		if (bitrate.equals("null")) {
			bot.sendMessage(channel, "Stream not online");
		} else {
			Integer bits = Integer.parseInt(bitrate.replaceAll("\\..*", ""));
			
			if (bits > 2200) {
				bot.sendMessage(channel, "Bitrate is: "+bits+" and is too damn high! Kappa");
			} else {
				bot.sendMessage(channel, "Bitrate is: "+bits);
			}
			
//			bot.sendMessage(channel, "Bitrate is: "+ Integer.parseInt(bitrate.replaceAll("\\..*", "")));
		}
	}

	@Override
	public String toString() { return getCommandName(); }
}
