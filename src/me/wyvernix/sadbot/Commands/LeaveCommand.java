package me.wyvernix.sadbot.Commands;

import me.wyvernix.sadbot.Bots.MasterBot;

public class LeaveCommand implements BotCommand {

	public String getCommandName() {
		return "leave";
	}

	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		System.out.println("halp");
		if (sender.equals("shady1765") || sender.equals("activeenergylive")) {
			System.out.println("#"+bot.getName());
			System.out.println(channel);
			if (channel.equalsIgnoreCase("#"+bot.getName())){
				System.out.println("wut");
				bot.sendMessage(channel, "this is my home! you get out!");
				bot.sendMessage(channel, ".timeout " + sender + " 60");
			}else{
				System.out.println("bai");
			bot.sendMessage(channel, "cya! :3");
			bot.partChannel(channel);
			}
		}
	}
	public String toString() { return getCommandName(); }
}
