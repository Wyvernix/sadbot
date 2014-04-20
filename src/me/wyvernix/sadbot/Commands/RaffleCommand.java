package me.wyvernix.sadbot.Commands;

import java.util.ArrayList;

import me.wyvernix.sadbot.Bots.MasterBot;

public class RaffleCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "raffle";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		String winner = "cake";
		
		ArrayList<String> users = bot.getActiveUsers();
		final ArrayList<String> mods = bot.getMods();
		
		users.remove(channel.replaceFirst("#", "")); //remove broadcaster
		users.remove(bot.botName.toLowerCase()); //remove bot
		
		if (!message.contains("mods")) { //remove mods unless told not to
			for (int i = 0; i < mods.size(); i++) {
				users.remove(mods.get(i));
			}
		}
		
		if (users.size() > 0) {
			winner = users.get((int)(Math.random() * ((users.size() - 1) + 1)));
			
//			bot.userStats.addWin(winner); //why do i track this? oh right. in case i ever add a trigger for no rerolls.
			
			bot.sendMessage(channel, winner + " is winrar!");
		} else { //all users are mods, so no winner
			bot.sendMessage(channel, "cake is winrar! uhh... ok then. go cake. (mods are excluded unless use' mods' modifier)");
		}
	}
}
