package me.wyvernix.sadbot.Commands;

import java.util.HashMap;
import java.util.Map;

import me.wyvernix.sadbot.Bots.MasterBot;

public class PollCommand implements BotCommand {
	
	private Map<String, Integer> choices = new HashMap<String, Integer>();
	private boolean isOpen = false;

	@Override
	public String getCommandName() {
		return "poll";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender,
			String message) {
		if (message.length() < 1){
			if (isOpen) {
				bot.sendMessage(channel, choices.keySet().toString());
			}
		} else if (message.startsWith("open") && bot.getMods().contains(sender)) {
			if (isOpen) {
				closePoll(bot, channel);
			}
			String[] parts = message.split(" \\| ");
			if (parts.length > 1) {
				parts[0] = parts[0].replace("open ", "");
				for (String thing : parts) {
					choices.put(thing, 0);
				}
				bot.sendMessage(channel, "POLL OPENED with choices: "+choices.keySet().toString().replaceAll("\\[|\\]", ""));
				isOpen = true;
			} else {
				bot.sendMessage(channel, "must supply options plz. !poll open cheese | ramen");
			}
		} else if (message.startsWith("close") && bot.getMods().contains(sender)) {
			closePoll(bot, channel);
		} else {
			for (String check : choices.keySet()) {
				if (message.equalsIgnoreCase(check)) {
					choices.put(check, choices.get(check)+1);
				}
			}
//			bot.sendMessage(channel, "open | close");
		}
	}
	
	private void closePoll(MasterBot bot, String channel) {
		if (isOpen) {
			isOpen = false;
			bot.sendMessage(channel, "POLL IS OVER! Results: " + choices.toString().replaceAll("\\{|\\}", ""));
			choices.clear();
		}
	}

	@Override
	public String toString() { return getCommandName(); }
}
