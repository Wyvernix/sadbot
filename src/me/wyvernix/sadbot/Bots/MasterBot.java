package me.wyvernix.sadbot.Bots;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.regex.Pattern;

import me.wyvernix.sadbot.BotManager;
import me.wyvernix.sadbot.GSONic;
import me.wyvernix.sadbot.JMegaMegaHal;
import me.wyvernix.sadbot.UserStats;
import me.wyvernix.sadbot.Util;
import me.wyvernix.sadbot.newGUI;
import me.wyvernix.sadbot.Commands.BotCommand;
import me.wyvernix.sadbot.Filters.ChatFilter;
import me.wyvernix.sadbot.Filters.LinkFilter;

import org.jibble.pircbot.*;
//import org.jibble.jmegahal.JMegaHal;


public class MasterBot extends PircBot {
	private static final String botVersion = "2.5.4";
	private MasterBot me = this;
	public boolean shutdown = false;
	private String oauth = "";
	
	//variables to be overridden
	public String botName;
	public String mainChan;
	private Color inColor = Color.BLACK;
	private Color outColor = Color.BLACK;

	//variables to be loaded 
	private List<BotCommand> commands; //Global/admin commands
	private List<BotCommand> sadCommands; //Channel commands, hard coded
	private Map<String,String> ccommands; //Custom Commands, "soft coded"
	public List<ChatFilter> filters;
	public UserStats userStats;
	public LinkFilter linkFilter;
	private JMegaMegaHal hal;
	
	private ArrayList<String> mods = new ArrayList<String>();
	private ArrayList<String> activeUsers = new ArrayList<String>();
	private ArrayList<String> newBuffer = new ArrayList<String>(); //buffer for new viewers, so userStats doesn't get flooded with lurkers
	private Map<String, Integer> chatLines = new HashMap<String, Integer>(32, 0.75f); 
	public ArrayList<String> specialUsers = new ArrayList<String>(); 
	
	private void firstRun() {
		//this is from the JMegaHal template. whatever. it adds some sentences to the brain.
		System.err.println("Couldn't find the brain so will use default data");
		hal = new JMegaMegaHal();
		hal.add("Hello World");
		hal.add("Can I have some coffee?");
		hal.add("Please slap me");
		try {
			hal.addDocument("https://dl.dropboxusercontent.com/u/26842546/getsmart.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//// gets and sets for extends
	protected void setBotName(String name) {
		botName = name;
	}
	/**
	 * Method setSadCommands sets global commands. 
	 * @param cc ArrayList<BotCommand>: ArrayList of global BotCommand.
	 */
	protected void setCommands(List<BotCommand> cc) {
		commands = cc;
	}
	/**
	 * Method setSadCommands sets main channel's commands. 
	 * @param cc ArrayList<BotCommand>: ArrayList of channel BotCommand.
	 */
	protected void setSadCommands(List<BotCommand> cc) {
		sadCommands = cc;
	}
	
	/**
	 * Method setMainChan sets main channel for bot.
	 * @param chan String: name of main channel.
	 */
	protected void setMainChan(String chan) {
		mainChan = chan;
	}
	/**
	 * Method setColors sets log colors for bot.
	 * @param in Color: input color
	 * @param out Color: output color
	 */
	protected void setColors(Color in, Color out) {
		inColor = in;
		outColor = out;
	}
	protected void setFilters(List<ChatFilter> fil) {
		filters = fil;
	}
	
	public ArrayList<String> getMods() {
		return mods;
	}
	public ArrayList<String> getActiveUsers() {
		return activeUsers;
	}
	//// end
	
	
	@SuppressWarnings("unchecked")
	public void init(String oa) {
		//this is run after all variables above are set.
		userStats = new UserStats(botName);
		linkFilter = new LinkFilter(botName);
		oauth = oa;
		System.out.println("Starting " + botName + ".");
		appendToPane("Starting "+botName+"\n", inColor);
		appendToPane("Commands: "+sadCommands.toString()+ "\n", inColor);
		appendToPane("Chat filters: "+filters.toString()+"\n", inColor);
		
		//load ccommands
		ccommands = (Map<String, String>) Util.load(botName+"Commands.dat");
		if (ccommands == null) {
			ccommands = new HashMap<String,String>(16, 0.80f);
		}
		// load brain
		hal = (JMegaMegaHal) Util.load(botName+".ser");
		if (hal == null) {
			firstRun();
		}
		
		this.setName(botName);
		this.setVersion("Sad_Bot v"+botVersion); // visible to whois and CTCP version
		this.setFinger("Get your hand off of me!");  // visible on CTCP finger
		this.setMessageDelay(1000); //plz don't ban me Twitch :P
		mods.add("shady1765"); //because twitch hates me. >bot admin here<
		
		//TODO scheduling, etc.
//        TimedMessageTask tmt1 = new TimedMessageTask("save");
//
//        timer = new Timer();
//        timer.schedule(tmt1, 0, 30 * 1000); // 30 secs
//        timer.schedule(tmt1, 0, 20 * 60 * 1000);  // 15 min
	}
	
	private void messageCommand(String channel, String sender, String message) {
		message = message.replaceFirst("!", "");
		if (channel.equalsIgnoreCase(mainChan) || channel.equalsIgnoreCase("#"+botName.toLowerCase())) {
			appendToPane(message+""+System.getProperty("line.separator"), Color.BLACK);
			
			for(BotCommand command : sadCommands) {
				// If the message starts with the command the BotCommand responds to, remove
				// the command from the message and pass the event along to the BotCommand.
				if(message.startsWith(command.getCommandName())) {
					command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim());
					return;
				}
			}
			
			//botName command start (mostly admin stuff)
			if (message.startsWith(botName.toLowerCase())) {
				if (message.length() < (botName).length()) {
					sendMessage(channel, "I am "+botName+" v" + botVersion + "! Now with coffee! Do !commands for more.");
					return;
				}
				//command had arguments, continue 
				message = message.replaceFirst(botName.toLowerCase()+" ", "");
				
			if (message.startsWith("command") && (mods.contains(sender))) {
				if (message.length() < "command ".length()){
					sendMessage(channel, sender + ", you are derp! Try add or remove");
					return;
				} else {
					final String[] split = message.split(" ");
					if (split[1].equals("add") && (message.length() > "command add".length())) {
						ccommands.put(split[2], message.replace("command add "+split[2]+" ", "").trim());
						sendMessage(channel, "Command: !"+ split[2] + " > " + message.replace("command add "+split[2]+" ", "").trim());
						Util.save(ccommands, botName+"Commands.dat");
						return;
					} else if (split[1].equals("remove") && (message.length() > "command remove".length())) {
						if (ccommands.get(split[2]) != null) {
						ccommands.remove(split[2]);
						sendMessage(channel, "Removed command: !" + split[2]);
						Util.save(ccommands, botName+"Commands.dat");
						return;
						} else {
							sendMessage(channel, "I cannot find the command '!" + split[2] + "' (use !command remove cake)");
							return;
						}
					} else if (split[1].equals("list")) {
						sendMessage(channel, "Custom commands: "+ccommands.keySet().toString().replace("[", "").replace("]", ""));
						return;
					} else {
						sendMessage(channel, sender + ", you are derp! Try add or remove. [syntax error]");
						return;
					}
				}
				
			} else if (message.startsWith("user") && mods.contains(sender)) {
				if (message.length() < "command ".length()){
					sendMessage(channel, sender + "... try info or list");
					return;
				} else {
					final String[] split = message.split(" ");
					split[2] = split[2].toLowerCase();
					if (split[1].equals("info") && (message.length() > "user info ".length())) {
						final Map<String,Object> userData = userStats.users.get(split[2]);
						if (userData != null) {
						this.sendMessage(channel, split[2]+": " + 
								Integer.toString((int)userData.get("viewTime")*5) + " minutes watched, " + 
								Integer.toString((int)userData.get("chatLines")) + " chat lines, " + 
								Integer.toString((int)userData.get("contestWins")) + " contest wins, and was last seen " 
								+userStats.lastSeen(split[2]) + ". and is " +
								userData.get("string1"));
						return;
						} else {
							this.sendMessage(channel, "User: "+split[2]+" not found.");
							return;
						}
					} else if (split[1].equals("list")) {
						this.sendMessage(channel, userStats.users.keySet().toString());
						return;
					}
				}
				
			} else if (message.startsWith("permit") && mods.contains(sender)) {
				//no one uses this anymore :P
				if (message.length() < "permit ".length()){
					sendMessage(channel, sender + "... try permitting a user");
					return;
				} else {
					final String[] split = message.split(" ");
					permitUser(channel, split[1]);
					return;
				}
			}
			//sender said something we dont know 
			this.sendMessage(channel, sender + ", what? [command/user/permit]");
			return;
			}
			//lazy, here is reg check command
			if (message.startsWith("amireg")) {
				if (userStats.isRegular(sender)) {
					sendMessage(channel, sender + " is a regular!");
				} else {
					sendMessage(channel, sender + " is not a regular.");
				}
			}
			
			if (message.startsWith("testo")) {
				String[] spaz = message.split(" ");
				spaz[1] = spaz[1].toLowerCase();
//				System.out.println(spaz[1]);
				Map<String, Object> vs = userStats.users.get(spaz[1]);
				
				int lines = (int)vs.get("chatLines");// + chatLines.get(spaz[1]);
				int vt = (int)vs.get("viewTime");
				sendMessage(channel, spaz[1] +": "+ ((vt*5 > 500) && ((float)lines/((float)vt*5f) >= 0.45f) && (!userStats.isRegular(spaz[1]))) + "*, vt: " +vt*5+ ", vt>500: "+ (vt*5>500) +
						", raito: " + ((float)lines/((float)vt*5f) >= 0.45f)+", is not reg: "+(!userStats.isRegular(spaz[1])) + ", ratio is: " + (float)lines/((float)vt*5f));
				
//				((vt > 500) && ((float)lines/((float)vt*5f) >= 0.45f) && (!isRegular(user)))
				
			}
			
			//checks for other(custom) channel commands
			if (!ccommands.isEmpty()) {
				final String[] split = ccommands.keySet().toArray(new String[1]);
				
			for (int i = 0; i < split.length; i++) {
				if (message.startsWith(split[i]) && (split[i].length() > 1)) {
					sendMessage(channel, "+ "+ccommands.get(split[i]));
					return;
				}
			}
			}
			/////////////
		}
		//commands in all channels:
		for(BotCommand command : commands) {
			if(message.startsWith(command.getCommandName())) { //maybe add global shady1765 override?
				command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim());
				return;
			}
		}
		//botmaster commands
		if (message.startsWith("add") && sender.equals("shady1765")) { //this is for me only. don't want other people to mess up bot. later i may add custom String
			try {
				hal.addDocument("https://dl.dropboxusercontent.com/u/26842546/getsmart.txt");
				Util.save(hal, botName+".ser");
				this.sendMessage(channel, "nom nom words :3");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
	}
	
	private void messageAI(String channel, String sender, String message) { //this is a mess.
			message = message.toLowerCase(); //makes bot more responsive
			message = message.replaceAll("(?i)(hey |)("+botName+")(, | |)", "");
			if (message.contains("random")) {
				sendMessage(channel, hal.getSentence());
			}else if (message.contains("hello") || message.startsWith("hi")) {
//			String helloUser = hal.getSentence(sender.replace("#", "").toLowerCase());
//			if (helloUser.toLowerCase().contains(sender)){
//				sendMessage(channel, helloUser);
//			} else {
				sendMessage(channel, "Hello " + sender.toLowerCase() + "!");
				
//				hal.add("Hello again, " + sender.replace("#", "").toLowerCase() + "!");
//				try {
//		    		// save the new data
//			    	ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
//					out.writeObject(hal);
//			        out.close();
//				} catch (FileNotFoundException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		} else if (message.matches("(?i)^((do|is|does)|(who|why|what|where|how|whose)) .*$")) {
			//message is a question
			if (message.matches("(?i)^why.*$")) {
				sendMessage(channel, hal.getSentence("Because"));
			} else if (message.matches("(?i)^(do(|es)|is|are) .*$")) {
				if (Math.random() <0.5){
					sendMessage(channel, "yes");	
				} else {
					if (Math.random() >0.1){
						sendMessage(channel, "no");
						} else {
							sendMessage(channel, "idk cake :3");
						}
				}
			}else{
			message = message.replaceAll("((do|who|why|what|where|how|whose|do|does|like)( |)|(\\?|\\.|\\!|,))", "")+" cake";
	
			String[] cleanBlock = message.split(" ");
			// "sadbot, what is x?"; responds with sentence(x), AND MORE!
			String baja = "cake";
			
			for (int i=0; i<(cleanBlock.length); i++) {
				if (cleanBlock[i].length() > 3) {
					baja = cleanBlock[i];
					break;
				}
			}
	
			sendMessage(channel, hal.getSentence(baja));
//			else {
//				float baka = Math.round(cleanBlock.length/2);
//				int baja;
//				baja=(int)baka;
//				if(baja > cleanBlock.length){
//					baja = baja-1;
//				}
//				sendMessage(channel, hal.getSentence(cleanBlock[baja]));
//			}
			}
		}
//		else if (message.contains("are you")) {
//			if (Math.random() <0.5){
//				sendMessage(channel, "yes");	
//			} else {
//				if (Math.random() >0.1){
//				sendMessage(channel, "no");
//				} else {
//					sendMessage(channel, "i am cake :3");
//				}
//			}
//		}
//		else if (message.contains("sadbot is ") || message.contains("sad_bot is ") || message.contains("energybot is ")) {
//			sendMessage(channel, hal.getSentence("sadbot").replaceAll("sadbot is", "I am") + " Kappa");
//			
//			hal.add(message.replaceAll("sad_bot", "sadbot").replaceAll("energybot", "sadbot"));
//			try {
//	    		// save the new data
//		    	ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
//				out.writeObject(hal);
//		        out.close();
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		} else if (message.contains("what is sadbot") || message.contains("what is sad_bot")) {
//			sendMessage(channel, hal.getSentence("sadbot") + " Kappa");
//		} else if (message.contains("what is energybot")) {
//			sendMessage(channel, hal.getSentence("sadbot").replaceAll("sadbot", "energybot") + " Kappa");
//		}
		else {
			String mesClean = message.replaceAll("( is | are | a | an | the | some | few | that | this | those | these | much | enough | each | every | either | neither | any | many )", " ");
			String[] cleanBlock = mesClean.replaceAll("(\\?|!|\\.|\")", "").split(" ");
			float baka = Math.round(cleanBlock.length>>1);
			int baja;
			baja=(int)baka;
			if(baja > cleanBlock.length){
				baja--;
			}
			sendMessage(channel, hal.getSentence(cleanBlock[baja]));
		}
	}
	
	protected boolean checkSpam(String channel, String sender, String message) {
    	boolean isPermitted = mods.contains(sender); //regular check is per-filter
    	if (!isPermitted) {
    		for(ChatFilter ffs : filters) {
				String returnStr = ffs.handleMessage(this, channel, sender, message, mods, specialUsers);
				if (returnStr!=null) {
					log(returnStr);
					return true;
				}
			}
    		return checkLinkFilter(channel, sender, message);
        }
		return false;
	}
	
	protected boolean checkLinkFilter(String channel, String sender, String message) { //seperated from others b/c AI loading
		String returnStr = linkFilter.handleMessage(this, channel, sender, message, mods, specialUsers);
		if (returnStr!=null) {
			log(returnStr);
			return true;
		}
		return false;
	}
	
	private void messageHandle(String channel, String sender, String message) {
		if (!chatLines.containsKey(sender)) {
			chatLines.put(sender, 0);
		}
		chatLines.put(sender, chatLines.get(sender) + 1);
		
		if (message.charAt(0) == "!".charAt(0)){ //is a command, handle it
			messageCommand(channel, sender, message);
		return;
		//if message doesnt START with '!', continue
		} else {
			if (checkSpam(channel, sender, message)) {
				return;
			}
			if (message.matches("^.*?youtu.*?(v=|/[0-9A-Za-z]{11}).*?$")) { //youtube link matching
				//System.out.println("match yt");
				int vid = message.indexOf("v=");
				String videoid = "dQw4w9WgXcQ"; //never gonna give you up
				if (vid > 0) {
					videoid = message.substring(vid+2, vid+13);
				} else {
					vid = message.indexOf("e/");
					videoid = message.substring(vid+2, vid+13);
				}
				
				
				String response = GSONic.getYoutube(videoid);
				if (response != null) {
					sendMessage(channel, "YouTube video linked: \"" + response);
				}
			} else if (message.matches("^.*?twitch\\.tv/.*?/\\w{1}/\\d{7}.*?$")) {  //twitch vod matching
				String[] url = message.split("/");
				String des = "", code = "", user = "";
				for (int i = 0; i < url.length; i++) {
					if (url[i].matches("\\w{1}")) {
						des = url[i];
						user = url[i-1];
					} else if (url[i].matches("\\d{7}.*")) {
						code = url[i].replace(" ", "");
					}
				}
				System.out.println(des + code);
				if ((des+code).length() > 6) {
					System.out.println(des + code + " is ok");
					String title = GSONic.getVideo(des.replace("b", "a")+code);
					if (!title.equals("null")) {
						sendMessage(channel, "Twitch VoD linked: \"" + title + "\" by "+user);
					}
					
				}
				
			} else if((Pattern.compile(Pattern.quote(botName), Pattern.CASE_INSENSITIVE).matcher(message).find())) {
				
				messageAI(channel, sender, message);
				return;
			} else if (message.matches("(?i)^.*?(hi|hey|good (evening|morning|afternoon)|sup|hello) (chat|everyone).*?$")) {
				sendMessage(channel, "hi " + sender + " <3");
			} else {	//not spam, etc, save message to AI
		  		hal.add(message.replace("\"", ""));
			}
		}
	}
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		log(sender + ": " + message);
		if (sender.equalsIgnoreCase(botName.toLowerCase())) {
			return;
		}
		message = message.trim();
		if(channel.equalsIgnoreCase(mainChan)) {
			if (!activeUsers.contains(sender)) {
				activeUsers.add(sender);
				manageUserList(true, sender);
			}
			if (newBuffer.contains(sender)) {
				newBuffer.remove(sender);
				userStats.add(sender);
				System.out.println(botName+": Added viewer: "+sender+" on "+userStats.lastSeen(sender)+".");
				appendToPane(botName+": Added viewer: "+sender+" on "+userStats.lastSeen(sender)+".\n", Color.black);
			}
			messageHandle(channel, sender, message);
		} else {
			if (message.charAt(0) == "!".charAt(0)){ //is a command, handle it
				messageCommand(channel, sender, message);
				return;
			} else if ((Pattern.compile(Pattern.quote(botName), Pattern.CASE_INSENSITIVE).matcher(message).find())) {
				
				messageAI(channel, sender, message);
				return;
			}
		}
	}
	
	@Override
	public void onJoin(String channel, String sender, String login, String hostname) {
		if (channel.equals(mainChan)) {
//		if (sender.equals("activeenergylive")) {
//			sendMessage(channel, "hi active :3 fun fact: 10% of followers are viewers! 10% of viewers are subscribers!");
//		}
		if (userStats.isNew(sender)) {
			newBuffer.add(sender);
		} else {
			userStats.updateLastSeen(sender);
		}
		if (activeUsers.contains(sender) == false) {
			activeUsers.add(sender);
			manageUserList(true, sender);
		}
		}
	}
	
	@Override
	public void onPart(String channel, String sender, String login, String hostname) {
		if (activeUsers.contains(sender)) {
			activeUsers.remove(sender);
			manageUserList(false, sender);
			if (newBuffer.contains(sender)) {
				newBuffer.remove(sender);
			} else {
				userStats.updateLastSeen(sender);
			}
		}
	}
	
	protected void manageUserList(boolean mode, String user) {
		//template. this must be overridden
		System.err.println("WARNING: USERLIST IS NOT OVERRIDDEN");
		if (mode) {
			//add user to gui
		} else {
			if (user.equals("*")) {
				//remove all users from gui
			} else {
				//remove user from gui
			}
		}
	}
	
	
	@Override
	public void onUserMode(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
		//Manage mods list
		String[] spaz = mode.split(" ");
		if (spaz[0].equals(mainChan)) {
		if (spaz[1].equals("+o")){
			if (!mods.contains(spaz[2])) {
				mods.add(spaz[2]);
			}
		}
		//writing mod list in permanent marker... twitch is terrible with mod orders
		//if twitch fixes TMI, uncomment following code
//		else if (spaz[1].equals("-o")) {
//			if (mods.contains(spaz[2])) {
//				mods.remove(spaz[2]);				
//			}
//		}
		}
	}
	
	@Override
	public void onUserList(String channel, User[] users)  {
		if (channel.equals(mainChan)) {
		for (User ussr : users) {
			String sender = ussr.getNick();
			if (userStats.isNew(sender)) {
				newBuffer.add(sender);
			} else {
				userStats.updateLastSeen(sender);
			}
			if (activeUsers.contains(sender) == false) {
				activeUsers.add(sender);
				manageUserList(true, sender);
			}
		}
		}
	}
	
	//archived for timed tasks
//	class TimedMessageTask extends TimerTask {
//        private String message = "";
//        
//        public TimedMessageTask(String message) {
//        	if (message.contains("hal")){
//        		this.message = hal.getSentence("cake");
////        	} else if (message.contains("save")) {
////        		try {
////	  				// save the new data
////	  				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
////	  				out.writeObject(hal);
////	  				out.close();
////	  			} catch (FileNotFoundException e) {
////	  				e.printStackTrace();
////	  			} catch (IOException e) {
////	  				e.printStackTrace();
////	  			}
//        	} else {
//            this.message = message;
//        	}
//        }
//        
//        public void run() {
//        	String[] channels = getChannels();
//        	for(int x=0; x < channels.length; x++) {
//        		sendMessage(channels[x], message);
//        	}
//        }
//        
//        @Override
//        public String toString() {
//			return null;
//        	
//        }
//    }
	
	@Override
	public void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target){
		sendNotice(sourceNick,"\001VERSION "+botName+":"+botVersion+":Shady1765\001");
	}
	
	@Override
	public void onDisconnect(){
		appendToPane("OMG "+this.getName()+" got disconnected!"+System.getProperty("line.separator"), Color.RED);
		manageUserList(false, "*");
		if (!shutdown) {
			tryReconnect();
		}
	}
	
	public void tryReconnect(){
		activeUsers.clear();
		boolean notConnected = true;
		int delay = (int)(Math.random()*10) +1;
		String[] servers = {"199.9.250.229","199.9.250.239","199.9.253.199","199.9.253.210"};
		
		while (notConnected) {
		    try {
		    	Thread.sleep(300*delay);
		    	System.out.println("Trying to reconnect "+botName+" "+delay);
		    	log("! ! Trying to reconnect "+botName+" "+delay);
//				reconnect();
		    	System.out.println("Trying "+ servers[delay % 4]);
		    	if (delay < 200) {
					delay++;
					System.out.println("we is ok?");
				}
		    	
		    	connect(servers[delay % 4], 6667, oauth); //this is what throws exception
				Thread.sleep(300);
				notConnected = !isConnected();
				
		    }
		    catch (Exception e) {
		        // Couldn't reconnect!
		        // Pause for 5 secs
		    	try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					//like this would ever happen. stupid java
				}
		    }
		}
		joinChannel("#"+botName.toLowerCase());
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	joinChannel(mainChan);
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	sendMessage("#"+botName.toLowerCase(), "im back BibleThump");
	}
	
	@Override
    public void log(String line) {
			if (line.startsWith(">>>") || line.startsWith("***")) {
				if (!line.startsWith(">>>PONG")){ //message received from server
					appendToPane(line+"\n", inColor);
				}
			}else{
				if ((line.charAt(0) == "#".charAt(0)) || (line.charAt(0) == "!".charAt(0))) { //error
					appendToPane(line+"\n", Color.red);
				}else
				if (!line.startsWith("PING")){ //message sent to server
					appendToPane(line+"\n", outColor);
				}
			}
	}
	
	@Override
    protected void onAction(String sender, String login, String hostname, String target, String action) { //equalivant to /me in chat
        checkSpam(target, sender, action);
    }
	
	public void globalBan(final String channel, final String name, String type) {
        String line = "!! Issuing a ban on " + name + " in " + channel + " for " + type;
        log(line);
        BotManager.globalBan(name, channel, type);
    }
    
    public void tempBan(final String channel, final String name, String type, int time) {
        String line = "!! Issuing a tempBan on " + name + " in " + channel + " for " + type;
        log(line);
        if (time <= 0){ //prevents default 600 timeout
        	time = 1;
        }
        sendMessage(channel, ".timeout "+name+" "+time*60);
    }
    
    public void purge(final String channel, final String name, String type) {
    	String line = "!! Issuing a purge on " + name + " in " + channel + " for "+type;
        log(line);
        sendMessage(channel, ".timeout "+name+" 1");
    }
	
	private synchronized void appendToPane(String msg, Color c) {
		newGUI.appendToPane(msg, c);
    }
	
	public void leaveServer() { //im sure ill find a use for this somewhere
		if (this.isConnected()) {
		this.disconnect();
		}
	}
	
	public void permitUser(String channel, final String user) {
		specialUsers.add(user.toLowerCase());
		sendMessage(channel, "Permitting user: "+user+" for one [banType] or 5 mins.");
		BotManager.timer.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		        if (specialUsers.contains(user)) {
		        	specialUsers.remove(user);
		        }
		    }
		}, 1000 * 60 * 5); //reset after 5 mins
	}
	
	
	public void saveAll() { //used on shutdown
		Map<String, Integer> cacheme = chatLines;
		chatLines.clear();
		userStats.updateStats(activeUsers, cacheme, me);
		if (!cacheme.isEmpty()) {
			Util.save(hal, botName+".ser");
		}
		System.out.println("Updated "+botName+" Stats.");
	}
}
