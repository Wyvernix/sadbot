package me.wyvernix.sadbot;
import java.awt.Color;
import java.io.IOException;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;

import me.wyvernix.sadbot.Bots.EnergyBot;
import me.wyvernix.sadbot.Bots.SadBot;

public class BotManager {
	static SadBot sadbot;
	static EnergyBot energybot;
	static newGUI gui;
//	private static GUI currentGUI = new GUI("SadBot PWNS");
	public static void main(String[] args) {
		gui = new newGUI();
		
		//start bot
		sadbot = new SadBot();
		
		//debug!
		sadbot.setVerbose(true);
		
		//connect to irc
		try {
			sadbot.connect("199.9.253.210", 6667, args[0]);
			
			Thread.sleep(100);
			//join channels
			sadbot.joinChannel("#sad_bot");
			Thread.sleep(100);
			sadbot.joinChannel("#shady1765");
			
			Thread.sleep(3000);
			sadbot.sendMessage("#sad_bot", "Sad_Bot has arrived! Kreygasm");
			
			Thread.sleep(500);
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

			Thread.sleep(100);
			//join channels
			energybot.joinChannel("#activeenergylive");
			Thread.sleep(100);
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
	
	@SuppressWarnings("static-access")
	public static void globalBan(String name, String channel, String type) {
		String line = "!! Issuing a ban on " + name + " in " + channel + " for " + type+"\n";
		gui.appendToPane(line, Color.red);
		energybot.sendMessage("#activeenergylive", ".ban "+name);
		energybot.sendMessage("#energybot", ".ban "+name);
		sadbot.sendMessage("#shady1765", ".ban "+name);
		sadbot.sendMessage("#sad_bot", ".ban "+name);
	}
}
