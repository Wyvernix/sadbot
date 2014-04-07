package me.wyvernix.sadbot;
import me.wyvernix.sadbot.Bots.EnergyBot;
import me.wyvernix.sadbot.Bots.SadBot;

public class MyBotMain {
//	private static GUI currentGUI = new GUI("SadBot PWNS");
	public static void main(String[] args) throws Exception{
        new newGUI();
		
		//start bot
		SadBot sadbot = new SadBot();
		
		//debug!
		sadbot.setVerbose(true);
		
		//connect to irc					dont lose this: ru88y4bf78v0xkwfz83k805my37po7r
		sadbot.connect("199.9.253.210", 6667, "oauth:9oxcnze4kfmv161zlz3hnl48d4hsmqk");
		
		Thread.sleep(100);
		//join #chan
		sadbot.joinChannel("#sad_bot");
		Thread.sleep(100);
		sadbot.joinChannel("#shady1765");
		
		Thread.sleep(3000);
		sadbot.sendMessage("#sad_bot", "Sad_Bot has arrived! Kreygasm");
		
		Thread.sleep(500);
		
		EnergyBot energybot = new EnergyBot();
		
		//debug!
		energybot.setVerbose(true);
		
		//connect to irc					dont lose this: ru88y4bf78v0xkwfz83k805my37po7r
		energybot.connect("199.9.253.199", 6667, "oauth:6mvg6l36ggtxifhjbkaxgddkwfqwnmx");
		
		Thread.sleep(100);
		//join #chan
		energybot.joinChannel("#activeenergylive");
		Thread.sleep(100);
		energybot.joinChannel("#energybot");
		
		Thread.sleep(3000);
		energybot.sendMessage("#energybot", "EnergyBot has arrived! Kreygasm");
	}
}
