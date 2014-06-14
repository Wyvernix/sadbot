package me.wyvernix.sadbot;
import java.awt.Color;
import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import me.wyvernix.sadbot.Bots.EnergyBot;
import me.wyvernix.sadbot.Bots.SadBot;

public class BotManager {
	private static SadBot sadbot;
	private static EnergyBot energybot;
	static newGUI gui;
	
	private static String sbOA, ebOA;
	
//	private static GUI currentGUI = new GUI("SadBot PWNS");
	public static void main(String[] args) {
		gui = new newGUI();
		
		newGUI.alert("Starting Bots!");
		
		//start bot
		sadbot = new SadBot();
		
		//debug!
		sadbot.setVerbose(true);
		
		//connect to irc
		try {
			sadbot.connect("199.9.253.210", 6667, args[0]);
			sbOA = args[0];
			
			Thread.sleep(100);
			//join channels
			sadbot.joinChannel("#sad_bot");
			
			sadbot.joinChannel("#shady1765");
			
			Thread.sleep(3000);
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
		} catch (InterruptedException e) {
			e.printStackTrace();
			newGUI.logError(e);
		}
		
		
		
		energybot = new EnergyBot();
		
		//debug!
		energybot.setVerbose(true);
		
		//connect to irc
		try {
			energybot.connect("199.9.253.199", 6667, args[1]);
			ebOA = args[1];
			
			Thread.sleep(100);
			//join channels
			energybot.joinChannel("#activeenergylive");
			energybot.joinChannel("#energybot");
			
			Thread.sleep(3000);
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
		} catch (InterruptedException e) {
			e.printStackTrace();
			newGUI.logError(e);
		}
		
	}
	
	public static void globalBan(String name, String channel, String type) {
		final String line = "!! Issuing a ban on " + name + " in " + channel + " for " + type+"\n";
		newGUI.appendToPane(line, Color.red);
		energybot.sendMessage("#activeenergylive", ".ban "+name);
		energybot.sendMessage("#energybot", ".ban "+name);
		sadbot.sendMessage("#shady1765", ".ban "+name);
		sadbot.sendMessage("#sad_bot", ".ban "+name);
	}
	
	public static void shutdown() {
		newGUI.appendToPane("Shutting down...\n", Color.red);
		energybot.disconnect();
		sadbot.disconnect();
		newGUI.alert("Stopping!");
		
		Runnable r = new Runnable() {
            public void run() {
    	    	try {
    				Thread.sleep(3000);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    	    	System.exit(0);
            }
        };
        new Thread(r).start();
	}
	
	public static void reconnect() {
		newGUI.appendToPane("Reconnecting Bots...\n", Color.red);
		energybot.disconnect();
		sadbot.disconnect();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		try {
			sadbot.connect("199.9.253.210", 6667, sbOA);
			
			Thread.sleep(100);
			//join channels
			sadbot.joinChannel("#sad_bot");
			sadbot.joinChannel("#shady1765");
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
		} catch (InterruptedException e) {
			e.printStackTrace();
			newGUI.logError(e);
		}
		
		try {
			energybot.connect("199.9.253.199", 6667, ebOA);

			Thread.sleep(100);
			//join channels
			energybot.joinChannel("#activeenergylive");
			energybot.joinChannel("#energybot");
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
		} catch (InterruptedException e) {
			e.printStackTrace();
			newGUI.logError(e);
		}
	}
}
