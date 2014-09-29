package me.wyvernix.sadbot.Commands;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import me.wyvernix.sadbot.GSONic;
import me.wyvernix.sadbot.Bots.MasterBot;

public class TweetCommand implements BotCommand {

	@Override
	public String getCommandName() {
		return "tweet";
	}

	@Override
	public void handleMessage(MasterBot bot, String channel, String sender, String message) {
		if (!bot.getMods().contains(sender)) {
			return;
		}
		String tweet = null;
		if (message.length() < 1) {
			tweet = "https://api-ssl.bitly.com/v3/shorten?access_token=09e4f66b34f51a5aff3e711788fd707c92339a89&longUrl=" +
					"https%3A%2F%2Ftwitter.com%2Fintent%2Ftweet%3Fstatus%3DHang%2Bout%2Bwith%2Bme%2Bat%2Btwitch.tv%252F" + channel.substring(1);
//			System.out.println(tweet);
		} else {
			try {
				tweet = "https://api-ssl.bitly.com/v3/shorten?access_token=09e4f66b34f51a5aff3e711788fd707c92339a89&longUrl=" +
						"https%3A%2F%2Ftwitter.com%2Fintent%2Ftweet%3Fstatus%3D" + URLEncoder.encode(message, "UTF-8");
//				System.out.println(tweet);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if (tweet != null)
			bot.sendMessage(channel, GSONic.getTweet(tweet));
	}

	@Override
	public String toString() { return getCommandName(); }
}