package me.wyvernix.sadbot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class UserStats {
	public Map<String, Map<String,Object>> users = new HashMap<String, Map<String,Object>>();
	private String botName;
	
	@SuppressWarnings("unchecked")
	public UserStats(String botName) {
		this.botName = botName;
		ObjectInputStream inp = null;
		final String commandsFile= "save\\"+botName+"UserStats.dat";
		try {
			File file = new File(commandsFile);
		    inp = new ObjectInputStream(new FileInputStream(file));
		    users = (Map<String, Map<String, Object>>) inp.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't find the data ("+ commandsFile +") so will fix?");
			saveData(users, commandsFile);
		} catch (IOException e) {
			System.err.println("Couldn't find the data ("+ commandsFile +") so will fix?");
			saveData(users, commandsFile);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(inp != null) {
				try {
					inp.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (users == null) {
				users.put("shady1765", this.genUserStats());
			}
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
		ussr.put("int1", (int)ussr.get("int1") + 1);
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
		return (int)users.get(user).get("int1");
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
		saveData(users,"save\\"+botName+"UserStats.dat");
	}
	
	public boolean isNew(String user) {
		return !users.containsKey(user);
	}
	
	public void updateStats(ArrayList<String> viewers, Map<String, Integer> chatters) {
		int sz = viewers.size();
		Map<String,Object> viewerStats;
		for(int i = 0; i < sz; i++) {
			String user = viewers.get(i);
			
			if (users.get(user) != null) {
				viewerStats = users.get(user);
				
				viewerStats.put("viewTime", (int)viewerStats.get("viewTime")+1);
				if (chatters.get(user) != null) {
					viewerStats.put("chatLines", (int)viewerStats.get("chatLines") + chatters.get(user));
				}
				
				users.put(viewers.get(i), viewerStats);
				
				removeWarning(user);
			}
		}
		
		
		saveData(users, "save\\"+botName+"UserStats.dat");
	}
	
	private void saveData(Object saveData2, String fileName) {
		try {
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(saveData2);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String,Object> getData(String user) {
		return users.get(user);
	}
	
	
}
