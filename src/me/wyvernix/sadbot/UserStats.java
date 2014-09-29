package me.wyvernix.sadbot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import me.wyvernix.sadbot.Bots.MasterBot;

public class UserStats {
	public Map<String, Map<String,Object>> users;
	private String botName;
	
	@SuppressWarnings("unchecked")
	public UserStats(String botName) {
		this.botName = botName;
		final String commandsFile= botName+"UserStats.dat";
		users = (Map<String, Map<String, Object>>) Util.load(commandsFile);
		if (users == null){
			users = new HashMap<String, Map<String,Object>>();
			users.put("shady1765", this.genUserStats());
		}
		
	}
	
	private Map<String,Object> genUserStats() {
		Map<String,Object> klow = new HashMap<String, Object>();
		
		klow.put("viewTime", 0);
		klow.put("chatLines", 0);
		klow.put("contestWins", 0);
		klow.put("lastSeen", Calendar.getInstance());
		klow.put("int1", 0);	//warnings
		klow.put("string1", "null");
		
		
		return klow;
	}
	
	public void addWarning(String user) {
		Map<String,Object> ussr = users.get(user);
		if (ussr == null) {
			add(user);
			ussr = users.get(user);
		}
		ussr.put("int1", (int)ussr.get("int1") + 1);
		users.put(user, ussr);
	}
	
	public void setRegular(String name, String status) {
		Map<String,Object> ussr = users.get(name);
		ussr.put("string1", status);
		users.put(name, ussr);
	}
	
	public boolean isRegular(String name) {
		Map<String,Object> ussr = users.get(name);
		if (ussr == null) {
			add(name);
			ussr = users.get(name);
		}
		return ussr.get("string1").equals("reg");
	}
	
	public void addWin(String user) {
		Map<String,Object> ussr = users.get(user);
		ussr.put("contestWins", (int)ussr.get("contestWins") + 1);
		users.put(user, ussr);
	}
	
	public void removeWarning(String user) {
		Map<String,Object> ussr = users.get(user);
		if ((int)ussr.get("int1") > 0) {
			ussr.put("int1", (int)ussr.get("int1") - 1);
			users.put(user, ussr);
		}
	}
	
	public int getWarnings(String user) {
		Map<String, Object> ss = users.get(user);
		if (ss != null) {
			return (int) ss.get("int1");
		} else {
			return 0;
		}
		
	}
	
	public String lastSeen(String user) {
		Map<String,Object> viewerData = users.get(user);
		Calendar lastSeen = ((Calendar) viewerData.get("lastSeen"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMMM, yyyy");
		
		return sdf.format(lastSeen.getTime());
	}
	
	public void updateLastSeen(String user) {
		Map<String,Object> ussr = users.get(user);
		ussr.put("lastSeen", Calendar.getInstance());
		users.put(user, ussr);
	}
	
	public void add(String user) {
		
		users.put(user, genUserStats());
		Util.save(users,botName+"UserStats.dat");
	}
	
	public boolean isNew(String user) {
		return !users.containsKey(user);
	}
	
	public void updateStats(ArrayList<String> viewers, Map<String, Integer> chatLines, MasterBot bot) {
		int sz = viewers.size();
		Map<String,Object> viewerStats;
		String user;
		for(int i = 0; i < sz; i++) {
			user = viewers.get(i);
			
			if (users.get(user) != null) {
				viewerStats = users.get(user);
				
				int vt = (int)viewerStats.get("viewTime")+1;
				
				viewerStats.put("viewTime", vt);
				if (chatLines.get(user) != null) {
					int lines = (int)viewerStats.get("chatLines") + chatLines.get(user);
//					System.out.println(user + ": *" + ((vt*5 > 500) && ((float)lines/((float)vt*5f) >= 0.45f) && (!isRegular(user))) +"*: "+ (float)lines/((float)vt*5));
					if ((vt*5 > 500) && ((float)lines/((float)vt*5f) >= 0.45f) && (!isRegular(user))) {
						setRegular(user, "reg");
//						bot.sendMessage(bot.mainChan, user.toUpperCase() + " IS A REGULAR! CONGRATS!");
						System.err.println(user.toUpperCase() + " is reg now.");
						System.out.println(isRegular(user));
					}
					
					viewerStats.put("chatLines", lines);
				}
				
				users.put(viewers.get(i), viewerStats);
				
				removeWarning(user);
			}
		}
		
		
		Util.save(users, botName+"UserStats.dat");
	}
	
	public Map<String,Object> getData(String user) {
		return users.get(user);
	}
	
	
}
