package me.wyvernix.sadbot.Commands.SadBot;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.wyvernix.sadbot.newGUI;
import me.wyvernix.sadbot.Bots.MasterBot;
import me.wyvernix.sadbot.Commands.BotCommand;


public class SadQuoteCommand implements BotCommand {
	private ArrayList<String> quotes = new ArrayList<String>();
	
	public String getCommandName() {
		return "quote";
	}
	
	@SuppressWarnings("unchecked")
	public SadQuoteCommand() {
		ObjectInputStream inp = null;
		final String quoteFile= "save\\sadQ.dat";
		try {
			File file = new File(quoteFile);
		    inp = new ObjectInputStream(new FileInputStream(file));
		    quotes = (ArrayList<String>) inp.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println("Couldn't find the data ("+ quoteFile +") so will fix?");
			saveData(quotes, quoteFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't find the data ("+ quoteFile +") so will fix?");
			saveData(quotes, quoteFile);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(inp != null) {
				try {
					inp.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		new Timer().schedule(new TimerTask() {         
		    @Override
		    public void run() {  
		    	System.out.println("Saving sadbot quotes...");
				saveData(quotes, quoteFile);
		    }
		}, 3000, 1000 * 60 * 5);
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		if (message.length() < 1){
			try{
				bot.sendMessage(channel, quotes.get((int)(Math.random() * ((quotes.size() - 1) + 1))));
			}catch(Exception e){
				e.printStackTrace();
				bot.sendMessage(channel, "huh. there aren't any quotes. try !quote add 'debx2 is derpy' -Shady");					
			}
		} else if (message.startsWith("add")) {
			if (!(message.replace("add ", "").charAt(0) == ".".charAt(0))){
			quotes.add(message.replace("add " , ""));
			bot.sendMessage(channel, "Added quote: "+message.replace("add " , ""));
			saveData(quotes, "save\\sadQ.dat");
			} else {
				bot.sendMessage(channel, sender + " is trying to hack me! BibleThump");
			}
		} else if (message.startsWith("remove") && bot.getMods().contains(sender)) {
			Boolean foundQuote = false;
			int sz = quotes.size();
			String replaceMe = message.replace("remove ", "");
			for(int x=0; x < sz; x++) {
				if (quotes.get(x).contains(replaceMe)){
					bot.sendMessage(channel, "Removed: " + quotes.get(x));
					quotes.remove(x);
					foundQuote = true;
					break;
				}
			}
			if (!foundQuote){
				bot.sendMessage(channel, "I couldn't find anything with: "+ message.replace("add " , ""));
			}
		} else if (message.startsWith("list") && bot.getMods().contains(sender)) {
			System.out.println(quotes.toString());
			newGUI.appendToPane(quotes.toString()+"\n", Color.BLACK);
			bot.sendMessage(channel, "check the logs for quotes.");
			
		} else {
			bot.sendMessage(channel, "You are a derp, "+sender+". Try !help");
		}
	}
	
	public void saveData(ArrayList<String> saveData2, String fileName) {
		try {
			// save the new data
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(saveData2);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String toString() { return getCommandName(); }
}
