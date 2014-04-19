package me.wyvernix.sadbot.Filters;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.wyvernix.sadbot.Bots.MasterBot;

public class SymbolFilter implements ChatFilter{
	private Pattern[] symbolsPatterns = new Pattern[2];
	private int symMin = 10, symPercent = 40;
	
	
	public SymbolFilter() {
		symbolsPatterns[0] = Pattern.compile("(\\p{InPhonetic_Extensions}|\\p{InLetterlikeSymbols}|\\p{InDingbats}|\\p{InBoxDrawing}|\\p{InBlockElements}|\\p{InGeometricShapes}|\\p{InHalfwidth_and_Fullwidth_Forms}|つ|°|ຈ|░|▀|▄|̰̦̮̠ę̟̹ͦͯͯ́ͮ̊̐͌̉͑ͨ̊́́̚|U̶̧ͩͭͧ͊̅̊ͥͩ̿̔̔ͥ͌ͬ͊͋ͬ҉|Ọ̵͇̖̖|A̴͍̥̳̠̞̹ͩ̋̆ͤͅ|E̡̛͚̺̖̪͈̲̻̠̰̳̐̿)");
        symbolsPatterns[1] = Pattern.compile("[!-/:-@\\[-`{-~]");
	}
	
	@Override
	public String handleMessage(MasterBot bot, String channel, String sender, String message, ArrayList<String> mods, Map<String, Object>special) {
		String messageNoWS = message.replaceAll("\\s", "");
		int count = getSymbolsNumber(messageNoWS);
		double percent = (double) count / messageNoWS.length();

		if (count > symMin && (percent * 100 > symPercent)) {
			String returns;
            int warningCount = bot.userStats.getWarnings(sender);
            if (warningCount > 0) {
            	bot.tempBan(channel, sender, "LINK", warningCount);
            	bot.sendMessage(channel, sender + ", please don't spam symbols - [temp ban]");
            	returns = "!T SYMTIMEOUT: " + sender + " in " + channel + " : " + message;
            } else {
            	bot.sendMessage(channel, sender + ", please don't spam symbols - [warning]");
            	returns = "!T SYMWARNING: " + sender + " in " + channel + " : " + message;
            }
            bot.userStats.addWarning(sender);

            return returns;
		}
		
		return null;
	}
	
	private int getSymbolsNumber(String s) {
        int symbols = 0;
        for (Pattern p : symbolsPatterns) {
            Matcher m = p.matcher(s);
            while (m.find())
                symbols += 1;
        }
        return symbols;
    }
}