package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Util;
import me.wyvernix.sadbot.Bots.MasterBot;

public class LinkFilterCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "link";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		// TODO Auto-generated method stub
		final String[] mesBlock = message.split(" ");
		if (!bot.getMods().contains(sender)) {
			return;
		}
		if (mesBlock.length > 1) {
			if (mesBlock[0].equals("add")) {
				bot.linkFilter.permitLink(mesBlock[1].toLowerCase());
				bot.sendMessage(channel, "Whitelisting link: "+mesBlock[1].toLowerCase());
				Util.save(bot.linkFilter.wlPatterns, bot.getName()+"Links.dat");
			} else if (mesBlock[0].equals("remove")) {
				if (bot.linkFilter.removeLink(mesBlock[1])) {
					bot.sendMessage(channel, "Removed Whitelisted link: "+mesBlock[1].toLowerCase());
					Util.save(bot.linkFilter.wlPatterns, bot.getName()+"Links.dat");
				} else{
					bot.sendMessage(channel, "Failed to find link ("+mesBlock[1].toLowerCase()+")");
				}
			} else if (mesBlock[0].equals("list")) {
				bot.sendMessage("#"+bot.getName(), bot.linkFilter.toString());
				System.out.println(bot.linkFilter.toString());
			}
		} else {
			bot.sendMessage(channel, "add / remove / list");
		}
	}

	@Override
	public String toString() { return getCommandName(); }
}
