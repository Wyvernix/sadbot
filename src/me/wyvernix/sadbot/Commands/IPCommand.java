package me.wyvernix.sadbot.Commands;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;


import me.wyvernix.sadbot.GSONic;
import me.wyvernix.sadbot.Bots.MasterBot;

public class IPCommand implements BotCommand {
	private String mainChan = "sad_bot";
	private String mainChanStatus;
	private boolean isChecking = false;
	private String response = "cake";
	
	public IPCommand(String chan){
		mainChan = chan;
		mainChanStatus = GSONic.getStatus(mainChan);
		getResponse();
		new Timer().schedule(new TimerTask() {		 
			@Override
			public void run() {
				mainChanStatus = GSONic.getStatus(mainChan);
				getResponse();
				System.out.println("Updated IP for "+mainChan);
			}
		}, 3000, 1000 * 60 * 15);
	}
	
	public String getCommandName() {
		return "ip";
	}

	private Boolean containStr(String text1, String statts) {
		return Pattern.compile(Pattern.quote(text1), Pattern.CASE_INSENSITIVE).matcher(statts).find();
	}
	
	private void getResponse() {
//		if (containStr("building", mainChanStatus) || (containStr("build", mainChanStatus) || containStr("creative", mainChanStatus) || containStr("wyv", mainChanStatus))) {
//			response = "Gallery: wyv.mcph.co [1.7.4]";
//		} else 
		if (containStr("hypixel", mainChanStatus)) {
			response = "mc.hypixel.net";
		} else if (containStr("hive", mainChanStatus)) {
			response = "play.hivemc.com";
		} else if (containStr("mineplex", mainChanStatus)) {
			response = "us.mineplex.com";
		} else if (containStr("single", mainChanStatus) || containStr("ssp", mainChanStatus)) {
			response = "this is single player! :3";
		} else {
			response = "wyv.mcph.co [1.7.4]";
		}
	}
	
	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		if (!isChecking) {
		isChecking = true;
		final String fixedChan = channel.replace("#", "");
		System.out.println(mainChan+" by "+bot.getName());
		if (message.length() < "ip ".length()){
			if (mainChan.equals("sad_bot")){
				bot.sendMessage(channel, "Let me find that for you...");
				mainChanStatus = GSONic.getStatus(fixedChan); 
				mainChan = fixedChan;
				getResponse();
			}
			
			if (mainChanStatus.equals("null")) {
				bot.sendMessage(channel, "hey shady, chanStatus failed...");
			} else {
				bot.sendMessage(channel, response);

				mainChanStatus = GSONic.getStatus(fixedChan); 
				final String ss = response; 
				getResponse();
				if (!ss.equals(response)) {
					bot.sendMessage(channel, "wait... no. sorry, it's: "+ response);
				}
			} 
		}
		isChecking = false;
	}
	}
	public String toString() { return getCommandName(); }
}
