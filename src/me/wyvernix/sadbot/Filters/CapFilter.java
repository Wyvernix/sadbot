package me.wyvernix.sadbot.Filters;

import java.util.ArrayList;
import java.util.Map;

import me.wyvernix.sadbot.Bots.MasterBot;

public class CapFilter implements ChatFilter {
	int minChars = 15;
	int maxPercent = 70;

	@Override
	public String handleMessage(MasterBot bot, String channel, String sender,	String message, ArrayList<String> mods, Map<String, Object>special) {
		String messageNoWS = message.replaceAll("\\s", "");
        int capsNumber = getCapsNumber(messageNoWS);
        double capsPercent = ((double) capsNumber / messageNoWS.length()) * 100;
        if (message.length() >= minChars && capsPercent >= maxPercent) {
            int warningCount = bot.userStats.getWarnings(sender);
            String returns; 
            if (warningCount > 0) {
            	bot.tempBan(channel, sender, "CAPS", warningCount);
            	bot.sendMessage(channel, sender + ", please don't shout or talk in all caps - [temp ban]");
            	returns = "!T CAPSTIMEOUT: " + sender + " in " + channel + " : " + message;
            } else {
            	bot.sendMessage(channel, sender + ", please don't shout or talk in all caps - [warning]");
            	returns = "!T CAPSWARNING: " + sender + " in " + channel + " : " + message;
            }
            bot.userStats.addWarning(sender);

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
