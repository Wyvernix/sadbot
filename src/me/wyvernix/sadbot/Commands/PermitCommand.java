package me.wyvernix.sadbot.Commands;

import java.util.Map;
import java.util.TimerTask;

import me.wyvernix.sadbot.Bots.MasterBot;

public class PermitCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "permit";
	}

	@Override
	public void handleMessage(final MasterBot bot, String channel, String sender, String message) {
		final String[] split = message.split(" ");
		System.out.println("::"+message+"::");
		if (split.length < 1){
			bot.sendMessage(channel, sender + "... try permitting a user");
			return;
		} else {
			
			Map<String, Object> spu = bot.getSpecialUsers();
			spu.put(split[0].toLowerCase(), true);
			bot.sendMessage(channel, "Permitting user: "+split[0]+" for one [banType] or 5 mins.");
			
			final Map<String, Object> tempo1 = spu;
			
			bot.timer.schedule(new TimerTask() {          
			    @Override
			    public void run() {
			    	Map<String, Object> spu = tempo1;
			    	
			        if (spu.containsKey(split[0])) {
			        	spu.remove(split[0]);
			        	bot.setSpecialUsers(spu);
			        }
			    }
			}, 1000 * 60 * 5); //reset after 5 mins
			return;
		}
	}
	public String toString() { return getCommandName(); }
}
