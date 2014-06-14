package me.wyvernix.sadbot.Filters;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.wyvernix.sadbot.BotManager;
import me.wyvernix.sadbot.Bots.MasterBot;

public class VineFilter implements ChatFilter {
	private Pattern vinePattern = Pattern.compile(".*(vine|4).*(4|vine).*Google.*", Pattern.CASE_INSENSITIVE);
	
	@Override
	public String handleMessage(MasterBot bot, String channel, String sender, String message, ArrayList<String> mods, Map<String, Object>special) {
		//Vine filter
//      String normalMessage = org.apache.commons.lang3.StringUtils.stripAccents(message);
      Matcher m = vinePattern.matcher(message.replaceAll(" ", ""));
      if (m.find()) {
          BotManager.globalBan(sender, channel, "VINE");
          return "!! VINEBAN: " + sender + " in " + channel + " : " + message;
      } else {
    	  return null;
      }
      
      
	}
	public String toString() { return "VineFilter"; }
}
