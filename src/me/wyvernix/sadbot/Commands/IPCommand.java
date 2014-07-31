package me.wyvernix.sadbot.Commands;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import me.wyvernix.sadbot.BotManager;
import me.wyvernix.sadbot.GSONic;
import me.wyvernix.sadbot.Util;
import me.wyvernix.sadbot.Bots.MasterBot;

public class IPCommand implements BotCommand {
	private String mainChan = "sad_bot";
	private String mainChanStatus;
	private boolean isChecking = false;
	private String response = "cake";
	private Map<String,String> ips;
	
	@SuppressWarnings("unchecked")
	public IPCommand(String chan, String botName){
		mainChan = chan;
		mainChanStatus = GSONic.getStatus(mainChan);
		
		try {
			ips = (Map<String,String>) Util.load(botName+"IPs.dat");
		} catch (Exception e) {
			e.printStackTrace();
		}
        if (ips == null) {
        	ips = new HashMap<String,String>();
        	
        	ips.put("mineplex", "us.mineplex.com lobby 9");
        	ips.put("paintball", "wyvpb.mcph.co");
        	
        	Util.save(ips, botName+"IPs.dat");
        }
		
		getResponse();
		BotManager.timer.schedule(new TimerTask() {		 
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
	
	private void getResponse() {
		response = "wyv.mcph.co [1.7.4]";
		for (String catcher : ips.keySet()) {
//			response = ips.get(catcher);
			
			if (mainChanStatus.matches("(?i).*?"+catcher+".*?")) {
				response = ips.get(catcher);
				break;
			}
		}
		//archive
//		if (containStr("hypixel", mainChanStatus)) {
//			response = "mc.hypixel.net";
//		} else if (containStr("hive", mainChanStatus)) {
//			response = "play.hivemc.com";
//		} else if (containStr("mineplex", mainChanStatus)) {
//			response = "us.mineplex.com lobby 9";
//		} else if (containStr("single", mainChanStatus) || containStr("ssp", mainChanStatus)) {
//			response = "this is single player! :3";
//		} else if (containStr("party", mainChanStatus)) {
//			response = "minecraftparty.com";
//		} else if (containStr("mcbrawl", mainChanStatus)) {
//			response = "mcbrawl.com";
//		} else if (containStr("wyv", mainChanStatus) && containStr("paintball", mainChanStatus)) {
//			response = "wyvpb.mcph.co";
//		} else {
//			response = "wyv.mcph.co [1.7.4]";
//		}
	}

	@Override
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
		} else if (bot.getMods().contains(sender)) {
			if (message.startsWith("add")) {
			String[] ss = message.split(" ");
			if (ss.length < 3) {
				bot.sendMessage(channel, "Not enough arguements");
				isChecking = false;
				return;
			}
			System.out.println(ss[1]+":::"+ message.replace("add "+ss[1]+" ", ""));
			bot.sendMessage(channel, "IP trigger '"+ss[1]+"' added");
			ips.put(ss[1], message.replace("add "+ss[1]+" ", ""));
			Util.save(ips, bot.botName+"IPs.dat");
		} else if (message.startsWith("remove")) {
			String[] ss = message.split(" ");
			if (ss.length < 2) {
				bot.sendMessage(channel, "Not enough arguements");
				isChecking = false;
				return;
			}
//			System.out.println(ss[1]+":::"+ message.replace("add "+ss[1]+" ", ""));
			if (ips.get(ss[1]) != null) {
				bot.sendMessage(channel, "Removing ip: "+ ss[1]);
				ips.remove(ss[1]);
				Util.save(ips, bot.botName+"IPs.dat");
			} else {
				bot.sendMessage(channel, "IP '"+ss[1]+"' not found");
			}
			
		} else if (message.startsWith("list")) {
			System.out.println(ips.keySet().toString());
			bot.sendMessage(channel, "Keywords: "+ips.keySet().toString().replace("[", "").replace("]", ""));
		} else {
			String[] ss = message.split(" ");
			String res = ips.get(ss[0]);
			if (res != null) {
				bot.sendMessage(channel, res);
			}
		}
		}
		isChecking = false;
	}
	}
	public String toString() { return getCommandName(); }
}
