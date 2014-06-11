package me.wyvernix.sadbot.Commands;

import java.util.ArrayList;

import me.wyvernix.sadbot.Bots.MasterBot;

public class RaffleCommand implements BotCommand {
	
	private ArrayList<String> users = new ArrayList<String>();
	private boolean isOpen = false;

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "raffle";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender,
			String message) {
		if (message.length() < 1){
			//TODO add to list
			if (isOpen) {
				if (!users.contains(sender)) {
					users.add(sender);
				}
			}
		} else if (message.startsWith("open") && bot.getMods().contains(sender)) {
			
			isOpen = true;
			bot.sendMessage(channel, "Raffle is now OPEN! Do !raffle to enter!");
			
		} else if (message.startsWith("close") && bot.getMods().contains(sender)) {
			isOpen = false;
			if (users.size() > 0) {
				String winner = users.get((int)(Math.random() * ((users.size() - 1) + 1)));
				bot.sendMessage(channel, winner + " is winrar! We had " + (new Integer(users.size())).toString() + " entries!");
			} else {
				bot.sendMessage(channel, "no entries, no winner BibleThump");
			}
			
			users.clear();
		} else {
			bot.sendMessage(channel, "open | close");
		}

	}

	@Override
	public String toString() { return getCommandName(); }
}
