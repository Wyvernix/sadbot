package me.wyvernix.sadbot;
import java.awt.Color;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import me.wyvernix.sadbot.Bots.EnergyBot;
import me.wyvernix.sadbot.Bots.SadBot;

public class BotManager {
	private static SadBot sadbot;
	private static EnergyBot energybot;
	public static newGUI gui;
	
	public static Timer timer = new Timer("Background Tasks");
	
//	private static String sbOA, ebOA;
	
//	private static GUI currentGUI = new GUI("SadBot PWNS");
	public static void main(String[] args) {
		gui = new newGUI();
//		String[] servers = {"199.9.250.229","199.9.250.239","199.9.253.199","199.9.253.210"};
		
		newGUI.alert("Starting Bots!");
		
		//start bot
		sadbot = new SadBot(args[0]);
		
		//debug!
		sadbot.setVerbose(true);
		
		//connect to irc
		try {
			sadbot.connect("199.9.250.229", 6667, args[0]);
//			sbOA = args[0];
			//join channels
			sadbot.joinChannel("#sad_bot");
			sadbot.joinChannel("#shady1765");
			
			sadbot.sendMessage("#sad_bot", "Sad_Bot has arrived! Kreygasm");
			
		} catch (NickAlreadyInUseException e) {
			System.err.println("SOMEONE STEALS MAH USERNAME SADBOT");
			e.printStackTrace();
			newGUI.logError(e);
		} catch (IOException e) {
			e.printStackTrace();
			newGUI.logError(e);
		} catch (IrcException e) {
			e.printStackTrace();
			newGUI.logError(e);
		}
		
		
		
		energybot = new EnergyBot(args[1]);
		
		//debug!
		energybot.setVerbose(true);
		
		//connect to irc
		try {
			energybot.connect("199.9.250.229", 6667, args[1]);
//			ebOA = args[1];
			//join channels
			energybot.joinChannel("#activeenergylive");
			energybot.joinChannel("#energybot");
			
			energybot.sendMessage("#energybot", "EnergyBot has arrived! Kreygasm");
		} catch (NickAlreadyInUseException e) {
			System.err.println("SOMEONE STEALS MAH USERNAME ENERGYBOT");
			e.printStackTrace();
			newGUI.logError(e);
		} catch (IOException e) {
			e.printStackTrace();
			newGUI.logError(e);
		} catch (IrcException e) {
			e.printStackTrace();
			newGUI.logError(e);
		}
		
		timer.schedule(new TimerTask() {         
		    @Override
		    public void run() {  
				sadbot.saveAll();
				energybot.saveAll();
		    }
		}, 3000, 1000 * 60 * 5);
		
		
//		timer.schedule(new TimerTask() {         
//		    @Override
//		    public void run() {
//		    	if (!sadbot.isConnected()) {
//					sadbot.tryReconnect();
//					sadbot.sendMessage("#shady1765", "stupid twitch keeps disconnecting me BibleThump");
//		    	}
//		    	if (!energybot.isConnected()) {
//		    		energybot.tryReconnect();
//					energybot.sendMessage("#activeenergylive", "stupid twitch keeps disconnecting me BibleThump");
//		    	}
//		    }
//		}, 1000 * 60, 1000 * 60);
	}
	
	public static void globalBan(String name, String channel, String type) {
		final String line = "!! Issuing a ban on " + name + " in " + channel + " for " + type+"\n";
		newGUI.appendToPane(line, Color.red);
		energybot.sendMessage("#activeenergylive", ".ban "+name);
//		energybot.sendMessage("#energybot", ".ban "+name);
		sadbot.sendMessage("#shady1765", ".ban "+name);
//		sadbot.sendMessage("#sad_bot", ".ban "+name);
	}
	
	public static void shutdown() {
		newGUI.appendToPane("Shutting down...\n", Color.red);
		newGUI.alert("Stopping!");
		timer.cancel();
		energybot.shutdown = true;
		sadbot.shutdown = true;
		energybot.disconnect();
		sadbot.disconnect();
		
		
		energybot.saveAll();
		sadbot.saveAll();
		
		Runnable r = new Runnable() {
            public void run() {
    	    	try {
    				Thread.sleep(2000);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    	    	System.exit(0);
            }
        };
        new Thread(r, "Sleep").start();
	}
	
	public static void reconnect() {
		newGUI.appendToPane("Reconnecting Bots...\n", Color.red);
		energybot.disconnect();
		sadbot.disconnect();
//		energybot.tryReconnect();
//		sadbot.tryReconnect();
		
//		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			sadbot.connect("199.9.253.210", 6667, sbOA);
//			
//			Thread.sleep(100);
//			//join channels
//			sadbot.joinChannel("#sad_bot");
//			sadbot.joinChannel("#shady1765");
//		} catch (NickAlreadyInUseException e) {
//			System.err.println("SOMEONE STEALS MAH USERNAME SADBOT");
//			e.printStackTrace();
//			newGUI.logError(e);
//		} catch (IOException e) {
//			e.printStackTrace();
//			newGUI.logError(e);
//		} catch (IrcException e) {
//			e.printStackTrace();
//			newGUI.logError(e);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			newGUI.logError(e);
//		}
//		
//		try {
//			energybot.connect("199.9.253.199", 6667, ebOA);
//
//			Thread.sleep(100);
//			//join channels
//			energybot.joinChannel("#activeenergylive");
//			energybot.joinChannel("#energybot");
//		} catch (NickAlreadyInUseException e) {
//			System.err.println("SOMEONE STEALS MAH USERNAME ENERGYBOT");
//			e.printStackTrace();
//			newGUI.logError(e);
//		} catch (IOException e) {
//			e.printStackTrace();
//			newGUI.logError(e);
//		} catch (IrcException e) {
//			e.printStackTrace();
//			newGUI.logError(e);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			newGUI.logError(e);
//		}
	}
}
