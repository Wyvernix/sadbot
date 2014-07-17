package me.wyvernix.sadbot.Filters;

import java.util.ArrayList;

import me.wyvernix.sadbot.Bots.MasterBot;

public class CapFilter implements ChatFilter {
	int minChars = 15;
	int maxPercent = 70;

	@Override
	public String handleMessage(MasterBot bot, String channel, String sender,	String message, ArrayList<String> mods, ArrayList<String> special) {
		if (bot.userStats.isRegular(sender)) {
			return null;
		}
		
		String messageNoWS = message.replaceAll("\\s", "");
        int capsNumber = getCapsNumber(messageNoWS);
        double capsPercent = ((double) capsNumber / messageNoWS.length()) * 100;
        if (message.length() >= minChars && capsPercent >= maxPercent) {
        	bot.userStats.addWarning(sender);
        	int warningCount = bot.userStats.getWarnings(sender);
            String returns; 
            if (warningCount > 1) {
            	bot.tempBan(channel, sender, "CAPS", warningCount);
            	bot.sendMessage(channel, sender + ", please don't shout or talk in all caps - [temp ban]");
            	returns = "!T CAPSTIMEOUT: " + sender + " in " + channel + " : " + message;
            } else {
            	bot.purge(channel, sender, "CAPS");
            	bot.sendMessage(channel, sender + ", please don't shout or talk in all caps - [warning]");//purge
            	returns = "!T CAPSWARNING: " + sender + " in " + channel + " : " + message;
            }
            return returns;
        }
		return null;
	}
	
	private int getCapsNumber(String s) {
        int caps = 0;
        int len = s.length();
        for (int i = 0; i < len; i++) {
            if (Character.isUpperCase(s.charAt(i))) {
                caps++;
            }
        }

        return caps;
    }
	
	public String toString() { return "CapFilter"; }
}
