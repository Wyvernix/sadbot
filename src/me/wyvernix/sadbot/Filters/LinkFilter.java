package me.wyvernix.sadbot.Filters;

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.wyvernix.sadbot.Util;
import me.wyvernix.sadbot.newGUI;
import me.wyvernix.sadbot.Bots.MasterBot;

public class LinkFilter implements ChatFilter {

	private Pattern[] linkPatterns = new Pattern[4];
	public ArrayList<Pattern> wlPatterns;

	@SuppressWarnings("unchecked")
	public LinkFilter(String botName) {
		linkPatterns[0] = Pattern.compile(".*http://.*", Pattern.CASE_INSENSITIVE);
        linkPatterns[1] = Pattern.compile(".*https://.*", Pattern.CASE_INSENSITIVE);
        linkPatterns[2] = Pattern.compile(".*[-A-Za-z0-9]+\\s?(\\.|\\(dot\\))\\s?(ac|ad|ae|aero|af|ag|ai|al|am|an|ao|aq|ar|as|asia|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|biz|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cat|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|com|coop|cr|cu|cv|cw|cx|cy|cz|de|dj|dk|dm|do|dz|ec|edu|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gov|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|info|int|io|iq|ir|is|it|je|jm|jo|jobs|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mil|mk|ml|mm|mn|mo|mobi|mp|mq|mr|ms|mt|mu|museum|mv|mw|mx|my|mz|na|name|nc|ne|net|nf|ng|ni|nl|no|np|nr|nu|nz|om|org|pa|pe|pf|pg|ph|pk|pl|pm|pn|post|pr|pro|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sx|sy|sz|tc|td|tel|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|travel|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|xxx|ye|yt|za|zm|zw)(\\W|$).*", Pattern.CASE_INSENSITIVE);
        linkPatterns[3] = Pattern.compile(".*(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\\s+|:|/|$).*");
        
        wlPatterns = (ArrayList<Pattern>) Util.load(botName+"Links.dat");
        if (wlPatterns == null) {
        	wlPatterns = new ArrayList<Pattern>();
        	permitLink("twitch.tv/"+botName);
        	Util.save(wlPatterns, botName+"Links.dat");
        }
	}
	
	@Override
	public String handleMessage(MasterBot bot, String channel, String sender, String message, ArrayList<String> mods, ArrayList<String> special) {
		if (containsLink(message)) {
            if (special.contains(sender)) {
            	special.remove(sender);
            	return "Link permitted ("+sender+")";
            } else {
            	bot.userStats.addWarning(sender);
            	int warningCount = bot.userStats.getWarnings(sender);
            	
            	String returns;
            	if (warningCount <= 1) {
            		bot.purge(channel, sender, "LINK");
                    bot.sendMessage(channel, sender + ", please ask a moderator before posting links - [purge]");//purge
                    returns = "!T LINKWARNING: " + sender + " in " + channel + " : " + message;
            	} else {
            		bot.tempBan(channel, sender, "LINK", warningCount);
            		bot.sendMessage(channel, sender + ", please ask a moderator before posting links - [temp ban]");
            		returns = "!T LINKTIMEOUT: " + sender + " in " + channel + " : " + message;
            	}
                return returns;
            }

        }
		return null;
	}
	
	public void permitLink(String ss) {
		wlPatterns.add(Pattern.compile(".*" + ss + ".*", Pattern.CASE_INSENSITIVE));
	}
	
	public boolean removeLink(String ss) {
		return wlPatterns.remove(Pattern.compile(".*" + ss + ".*", Pattern.CASE_INSENSITIVE));
	}
	
	public void permitLinks(ArrayList<Pattern> pat) {
		wlPatterns.addAll(pat);
	}
	
	private boolean containsLink(String message) {
        String[] splitMessage = message.toLowerCase().split(" ");
        for (String m : splitMessage) {
            for (Pattern pattern : linkPatterns) {
                //System.out.println("Checking " + m + " against " + pattern.pattern());
                Matcher match = pattern.matcher(m);
                if (match.matches()) {
                    newGUI.appendToPane("RB: Link match on " + pattern.pattern()+"\n", Color.CYAN.darker());
                    
                    // check if whitelisted
                    for (Pattern white : wlPatterns) {
                        //System.out.println("Checking " + m + " against " + pattern.pattern());
                        match = white.matcher(m);
                        if (match.matches()) {
                        	newGUI.appendToPane("RB: Link whitelist match on " + white.pattern()+"\n", Color.CYAN.darker());
                        	return false;
                        }
                    
                    }
                    return true;
                }
            }
        }
//        for (Pattern pattern : linkPatterns) {
//            System.out.println("Checking " + message + " against " + pattern.pattern());
//            Matcher match = pattern.matcher(message);
//            if (match.matches()) {
//                System.out.println("Bypass match");
//                return true;
//            }
//        }
        return false;
    }
	public String toString() { return "LinkFilter"; }
}
