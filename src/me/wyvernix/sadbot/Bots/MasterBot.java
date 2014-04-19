package me.wyvernix.sadbot.Bots;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import me.wyvernix.sadbot.BotManager;
import me.wyvernix.sadbot.UserStats;
import me.wyvernix.sadbot.newGUI;
import me.wyvernix.sadbot.Commands.BotCommand;
import me.wyvernix.sadbot.Filters.ChatFilter;

import org.jibble.pircbot.*;
import org.jibble.jmegahal.JMegaHal;


public class MasterBot extends PircBot {
	private static final String botVersion = "2.4.0";
	private static String botName = "Sad_Bot";
	protected String mainChan;
	private Color inColor = Color.BLACK;
	private Color outColor = Color.BLACK;
	
	protected ArrayList<String> mods = new ArrayList<String>();
	private ArrayList<String> activeUsers = new ArrayList<String>();
	public UserStats userStats;
	private Map<String, Integer> chatters = new HashMap<String, Integer>(); 
	private Map<String, Object> specialUsers = new HashMap<String, Object>();
	private Timer timer = new Timer();
	
	private static String BRAIN;
	JMegaHal hal = new JMegaHal();
	
	private void firstRun() {
		System.err.println("Couldn't find the brain ("+ BRAIN +") so will use default data");
		hal.add("Hello World");
		hal.add("Can I have some coffee?");
		hal.add("Please slap me");
		try {
			hal.addDocument("https://dl.dropboxusercontent.com/u/26842546/getsmart.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO
	//// gets and sets for extends
	void setBotName(String name) {
		botName = name;
	}
	/**
	 * Method setSadCommands sets global commands. 
	 * @param cc ArrayList<BotCommand>: ArrayList of global BotCommand.
	 */
	void setCommands(List<BotCommand> cc) {
		commands = cc;
	}
	/**
	 * Method setSadCommands sets main channel's commands. 
	 * @param cc ArrayList<BotCommand>: ArrayList of channel BotCommand.
	 */
	void setSadCommands(List<BotCommand> cc) {
		sadCommands = cc;
	}
	
	/**
	 * Method setMainChan sets main channel for bot.
	 * @param chan String: name of main channel.
	 */
	void setMainChan(String chan) {
		mainChan = chan;
	}
	/**
	 * Method setColors sets log colors for bot.
	 * @param in Color: input color
	 * @param out Color: output color
	 */
	void setColors(Color in, Color out) {
		inColor = in;
		outColor = out;
	}
	void setFilters(List<ChatFilter> fil) {
		filters = fil;
	}
	//// end
	////////////|commands|
	private List<BotCommand> commands;
	private List<BotCommand> sadCommands;
	private Map<String,String> ccommands = new HashMap<String,String>();
    private List<ChatFilter> filters;
	/////////////END
	
	@SuppressWarnings("unchecked")
	public void  init() {
		userStats = new UserStats(botName);
		System.out.println("Starting " + botName + ".");
		
		//////////////////////////|load commands|
		//load ccommands
//		ccommands.put("Commands", "lol");
		ObjectInputStream inp = null;
		final String commandsFile= "save\\"+botName+"Commands.dat";
		try {
			final File file = new File(commandsFile);
		    inp = new ObjectInputStream(new FileInputStream(file));
		    ccommands = (Map<String,String>) inp.readObject();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't find the data ("+ commandsFile +") so will fix?");
			saveData(ccommands, commandsFile);
		} catch (IOException e) {
			System.err.println("Couldn't find the data ("+ commandsFile +") so will fix?");
			saveData(ccommands, commandsFile);
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
		}
		//////////////////////END
		
		
		// load brain
		BRAIN = "save\\"+botName+".ser";
		ObjectInputStream in = null;
		try {
			final File file = new File(BRAIN);
		    in = new ObjectInputStream(new FileInputStream(file));
			hal = (JMegaHal) in.readObject();
		} catch (FileNotFoundException e) {
			firstRun();
		} catch (IOException e) {
			firstRun();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		this.setName(botName);
		this.setVersion("Sad_Bot v"+botVersion); // visible to whois and CTCP version
		this.setFinger("Get your hand off of me!");  // visible on CTCP finger
		
		this.setMessageDelay(2000); //plz don't ban me Twitch :P
		
		mods.add("shady1765"); //because twitch hates me.
		
		//TODO timer stuff.
//        TimedMessageTask tmt1 = new TimedMessageTask("save");
//
//        timer = new Timer();
//        timer.schedule(tmt1, 0, 30 * 1000); // 30 secs
//        timer.schedule(tmt1, 0, 20 * 60 * 1000);  // 15 min
		
		
		timer.schedule(new TimerTask() {         
		    @Override
		    public void run() {  
		    	System.out.println("Updated "+botName+" Stats.");
				userStats.updateStats(activeUsers, chatters);
				chatters.clear();
		    }
		}, 3000, 1000 * 60 * 5);
	}
	
	private void messageCommand(String channel, String sender, String message) {
		message = message.replaceFirst("!", "");
		if (channel.equalsIgnoreCase(mainChan) || channel.equalsIgnoreCase("#"+botName.toLowerCase())) {
			appendToPane(message+""+System.getProperty("line.separator"), Color.BLACK);
			
			for(BotCommand command : sadCommands) {

				// If the message starts with the command the BotCommand responds to, remove
				// the command from the message and pass the event along to the BotCommand.
				if(message.startsWith(command.getCommandName())) {
					command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim(), mods);
					return;
				}
			}
			
			//botName command start
			if (message.startsWith(botName.toLowerCase())) {
				if (message.length() < (botName+" ").length()) {//TODO check if it works
					sendMessage(channel, "I am "+botName+" v" + botVersion + "! Now with coffee! Do !commands for more.");
					return;
				}
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
						saveData(ccommands, "save\\"+botName+"Commands.dat");
						return;
					} else if (split[1].equals("remove") && (message.length() > "command remove".length())) {
						if (ccommands.get(split[2]) != null) {
						ccommands.remove(split[2]);
						sendMessage(channel, "Removed command: !" + split[2]);
						saveData(ccommands, "save\\"+botName+"Commands.dat");
						return;
						} else {
							sendMessage(channel, "I cannot find the command '!" + split[2] + "' (use !command remove cake)");
							return;
						}
					} else if (split[1].equals("list")) {
						sendMessage(channel, "Custom commands: "+ccommands.keySet().toString().replace("[", "").replace("]", ""));
						return;
					} else {
						sendMessage(channel, sender + ", you are derp! [syntax error]");
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
								+userStats.lastSeen(split[2]));
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
				if (message.length() < "permit ".length()){
					sendMessage(channel, sender + "... try permitting a user");
					return;
				} else {
					final String[] split = message.split(" ");
					specialUsers.put(split[1].toLowerCase(), true);
					sendMessage(channel, "Permitting user: "+split[1]+" for one break or 5 mins.");
					timer.schedule(new TimerTask() {          
					    @Override
					    public void run() {
					        if (specialUsers.containsKey(split[1])) {
					        	specialUsers.remove(split[1]);
					        }
					    }
					}, 1000 * 60 * 5); //reset after 5 mins
					return;
				}
			}
			//sender said something we dont know 
			this.sendMessage(channel, sender + ", what? [command/user/pemit]");
			System.out.println(message.startsWith("permit"));
			return;
			}
			
			//check commands?
			if (!ccommands.isEmpty()) {
				final String[] split = ccommands.keySet().toArray(new String[1]);
				
			for (int i = 0; i < split.length; i++) {
				if (message.startsWith(split[i]) && (split[i].length() > 1)) {
//					System.out.println(split[i] + " and message: "+message);
					sendMessage(channel, "+ "+ccommands.get(split[i]));
					return;
				}
			}
			}
			
			/////////////
		}
		//commands in all channels:
		for(BotCommand command : commands) {

			// If the message starts with the command the BotCommand responds to, remove
			// the command from the message and pass the event along to the BotCommand.
			if(message.startsWith(command.getCommandName())) { //maybe add global shady1765 override?
				command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim(), mods);
				return;
			}
		}
		//bot admin commands
		if (message.startsWith("add") && sender.equals("shady1765")) { //this is for me only. don't want other people to mess up bot. later i may add custom String
			try {
				hal.addDocument("https://dl.dropboxusercontent.com/u/26842546/getsmart.txt");
  				// save the new data
  				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
  				out.writeObject(hal);
  				out.close();
  				sendMessage(channel, "Nom nom words :3");
  			} catch (FileNotFoundException e) {
  				e.printStackTrace();
  			} catch (IOException e) {
  				e.printStackTrace();
  			}
			return;
		}
	}
	
	private void messageAI(String channel, String sender, String message) {
			message = message.toLowerCase(); //makes bot more responsive
			if (message.contains("random")) {
				sendMessage(channel, hal.getSentence());
			}else 
		if (message.startsWith("hey") || message.contains("hello") || message.startsWith("hi")) {
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
		} else if (message.startsWith("do") || message.contains("who") || message.contains("why") || message.contains("what") || message.contains("where") || message.contains("how") || message.contains("whose") || (message.startsWith("is")) || (message.startsWith("does"))) {
			//message is a question
			message = message.replaceAll("("+botName+")(, | )", "");
			if (message.startsWith("why") || message.startsWith("Why")) {
				sendMessage(channel, hal.getSentence("Because"));
	
			} else if (message.contains("do") || message.contains("does")) {
				if (Math.random() <0.5){
					sendMessage(channel, "yes");	
				} else {
					sendMessage(channel, "no");
				}
			}else{
				
			message = message.replaceAll("(do|who|why|what|where|how|whose|do|does|like)(,| |\\?)", "").replace("?", "").replace(".", "").replace("!", "").replace(",", "")+" cake";
	
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
		} else if (message.startsWith("are you")) {
			if (Math.random() <0.5){
				sendMessage(channel, "yes");	
			} else {
				if (Math.random() >0.1){
				sendMessage(channel, "no");
				} else {
					sendMessage(channel, "i am cake :3");
				}
			}
		}
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
			String mesClean = message.replaceAll(botName, "").replaceAll("( is | are | a | an | the | some | few | that | this | those | these | much | enough | each | every | either | neither | any | many )", " ");
			String[] cleanBlock = mesClean.replace("?", "").replace("!", "").replace(".", "").replace("\"","").split(" ");
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
    	boolean isPermitted = mods.contains(sender);
    	if (!isPermitted) {
    		for(ChatFilter ffs : filters) {
				String returnStr = ffs.handleMessage(this, channel, sender, message, mods, specialUsers);
				if (returnStr!=null) {
					log(returnStr);
					return true;
				}
			}
        }
		return false;
	}
	
	private void messageHandle(String channel, String sender, String message) {
		log(message);
		if (sender.equals(botName)) {
			return;
		}
		if (!chatters.containsKey(sender)) {
			chatters.put(sender, 0);
		}
		chatters.put(sender, chatters.get(sender) + 1);
		
		message = message.trim();
		
		if (message.charAt(0) == "!".charAt(0)){ //is a command, handle it
			messageCommand(channel, sender, message);
		return;
		//if message doesnt START with '!', continue
		} else {
			//TODO spam filter
			if (checkSpam(channel, sender, message)) {
				return;
			}
			
			if((Pattern.compile(Pattern.quote(botName), Pattern.CASE_INSENSITIVE).matcher(message).find())) {
				
				messageAI(channel, sender, message);
				return;
			
			} else if(channel.equalsIgnoreCase(mainChan)){	//save message to AI
		  		
		  		
		  		//TODO remove bad spam filter
//				Pattern rer = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[\\-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9\\.\\-]+|(?:www\\.|[\\-;:&=\\+\\$,\\w]+@)[A-Za-z0-9\\.\\-]+)((?:\\/[\\+~%\\/\\.\\w\\-_]*)?\\??(?:[\\-\\+=&;%@\\.\\w_]*)#?(?:[\\.\\!\\/\\\\\\w]*))?)");
//				Matcher match = rer.matcher(message);
				//
		  		
//		  		if (!match.find()){
		  			// add the new data to the brain
//		  			sendMessage(channel, "not found: " + message);
		  			hal.add(message.replace("\"", ""));
		  			try {
		  				// save the new data
		  				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
		  				out.writeObject(hal);
		  				out.close();
		  			} catch (FileNotFoundException e) {
		  				e.printStackTrace();
		  			} catch (IOException e) {
		  				e.printStackTrace();
		  			}
//		  		}
			}
		}
	}
	
	
	@Override
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		messageHandle(channel, sender, message);
	}
	
	
	
	@Override
	public void onJoin(String channel, String sender, String login, String hostname) {
		if (channel.equals(mainChan)) {
		if (sender.equals("activeenergylive")) {
			sendMessage(channel, "hi active :3");
		}
		
		if (userStats.isNew(sender)) {
			userStats.add(sender);
			System.out.println(botName+": Added viewer: "+sender+" on "+userStats.lastSeen(sender)+".");
			
		} else {
			userStats.updateLastSeen(sender);
		}
		if (!activeUsers.contains(sender)) {
			activeUsers.add(sender);
		}
		}
	}
	
	@Override
	public void onPart(String channel, String sender, String login, String hostname) {
		if (activeUsers.contains(sender)) {
			activeUsers.remove(sender);
			userStats.updateLastSeen(sender);
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
		} else if (spaz[1].equals("-o")) {
			if (mods.contains(spaz[2])) {
				mods.remove(spaz[2]);				
			}
		}
		}
	}
	
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
		boolean notConnected = false;
		int delay = 1;
		while (notConnected) {
		    try {
		    	Thread.sleep(300*delay);
				reconnect();
				Thread.sleep(300);
				notConnected = !isConnected();
				if (delay < 200) {
					delay++;
				}
		    }
		    catch (Exception e) {
		        // Couldn't reconnect!
		        // Pause for 5 secs
		    	try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
				}
		    }
		}

		joinChannel("#"+botName);
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
    	sendMessage("#"+botName, "im back BibleThump");
	}
	
	@Override
    public void log(String line) {
			if (line.startsWith(">>>") || line.startsWith("***")) {
//				if (line.startsWith(">>>PASS")) {
//					appendToPane(">>>PASS oauth:***"+System.getProperty("line.separator"), Color.BLUE.darker());
//				}else 
				if (!line.startsWith(">>>PONG")){
					appendToPane(line+"\n", inColor);
				}
			}else{
				if ((line.charAt(0) == "#".charAt(0)) || (line.charAt(0) == "!".charAt(0))) {
					appendToPane(line+"\n", Color.red);
				}else
				if (!line.startsWith("PING")){
					appendToPane(line+"\n", outColor);
				}
			}
	}
	
	@Override
    protected void onAction(String sender, String login, String hostname, String target, String action) {
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
        sendMessage(channel, ".timeout "+name+" "+time*120);
    }
	
	private void appendToPane(String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = newGUI.tPane.getDocument().getLength();
        newGUI.tPane.setCaretPosition(len);
        newGUI.tPane.setCharacterAttributes(aset, false);
        newGUI.tPane.replaceSelection(msg);
    }
	
	public void saveData(Object saveData2, String fileName) {
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
	
	public void leaveServer() throws InterruptedException {
		if (this.isConnected()) {
		Thread.sleep(100);
		this.disconnect();
		}
	}
}
