package me.wyvernix.sadbot.Commands;

import java.awt.Color;
import java.util.ArrayList;
import me.wyvernix.sadbot.Util;
import me.wyvernix.sadbot.newGUI;
import me.wyvernix.sadbot.Bots.MasterBot;

public class QuoteCommand implements BotCommand {
	private ArrayList<String> quotes = new ArrayList<String>();
	private static String quoteFile;
	
	@Override
	public String getCommandName() {
		return "quote";
	}
	
	@SuppressWarnings("unchecked")
	public QuoteCommand(String file) {
		quoteFile = file;
	    quotes = (ArrayList<String>) Util.load(quoteFile);
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		if (message.length() < 1){
			try{
				int qt = (int) (Math.random() * ((quotes.size() - 1) + 1));
				bot.sendMessage(channel, "Quote #"+ (new Integer(qt+1)).toString() + ": "+ quotes.get(qt));
			} catch (IndexOutOfBoundsException e){
				//ok. there are no quotes
				bot.sendMessage(channel, "huh. there aren't any quotes. try !quote add \"debx2 is derpy\" -Shady");					
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (message.startsWith("add ")) {
			if (!(message.replace("add ", "").charAt(0) == ".".charAt(0))){
			quotes.add(message.replace("add " , ""));
			bot.sendMessage(channel, "Added quote #"+ (new Integer(quotes.size())) +": "+message.replace("add " , ""));
			Util.save(quotes, quoteFile);
			} else {
				bot.sendMessage(channel, sender + " is trying to hack me! BibleThump");
			}
		} else if (message.startsWith("remove ") && bot.getMods().contains(sender)) {
			String[] spaz = message.split(" ");
			if (spaz[1].matches("^[0-9]*$")) {
				try {
					int qt = Integer.parseInt(spaz[1]) - 1;
				quotes.get(qt); //test if exists
				
				bot.sendMessage(channel, "Removed: " + quotes.get(qt));
				quotes.remove(qt);
				Util.save(quotes, quoteFile);
				return;
				
				} catch (IndexOutOfBoundsException e) {
					//doesnt exist, continue. maybe it was part of quote...
				}
				
			}
			
			boolean foundQuote = false;
			int sz = quotes.size();
			String replaceMe = message.replace("remove ", "");
			for(int x=0; x < sz; x++) {
				if (quotes.get(x).contains(replaceMe)){
					bot.sendMessage(channel, "Removed: " + quotes.get(x));
					quotes.remove(x);
					foundQuote = true;
					Util.save(quotes, quoteFile);
					break;
				}
			}
			if (!foundQuote){
				bot.sendMessage(channel, "I couldn't find anything with: "+ message.replace("remove " , ""));
			}
		} else if (message.startsWith("list") && bot.getMods().contains(sender)) {
			System.out.println(quotes.toString());
			newGUI.appendToPane(quotes.toString()+"\n", Color.BLACK);
			bot.sendMessage(channel, "check the logs for quotes.");
			
		} else {
			String[] spaz = message.split(" ");
			if (spaz[0].matches("^[0-9]*$")) {
				try{
					bot.sendMessage(channel, quotes.get(Integer.parseInt(spaz[0]) - 1));
				} catch (IndexOutOfBoundsException e){
					bot.sendMessage(channel, "quote doesn't exist! :3");					
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				bot.sendMessage(channel, sender + " is derpy! BibleThump");
			}
		}
	}

	@Override
	public String toString() { return getCommandName(); }
}
