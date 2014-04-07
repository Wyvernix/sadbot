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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import me.wyvernix.sadbot.UserStats;
import me.wyvernix.sadbot.newGUI;
import me.wyvernix.sadbot.Commands.*;
import me.wyvernix.sadbot.Commands.EnergyBot.*;

import org.jibble.jmegahal.JMegaHal;
import org.jibble.pircbot.*;


public class EnergyBot extends PircBot {
	private static final String botVersion = "2.3.3";
	private static String botName = "EnergyBot";
	
	private ArrayList<String> mods = new ArrayList<String>();
	private ArrayList<String> activeUsers = new ArrayList<String>();
	public UserStats userStats = new UserStats(botName);
	private Map<String, Integer> chatters = new HashMap<String, Integer>(); 
	
	private static String BRAIN = "save\\brain.ser";
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
	
	
	////////////
	private final List<BotCommand> commands;
	private final List<BotCommand> sadCommands;
	private Map<String,String> ccommands = new HashMap<String,String>(200);
	/////////////
	
	@SuppressWarnings("unchecked")
	public EnergyBot() {
		System.out.print("Starting " + botName + ".\n");
		
		//////////////////////////
		//TODO commands
		//load ccommands
//		ccommands.put("Commands", "lol");
		ObjectInputStream inp = null;
		final String commandsFile= "save\\energyCommands.dat";
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
		//
		//global commands
		commands = new ArrayList<BotCommand>();
		
		commands.add(new JoinCommand());
		commands.add(new LeaveCommand());
		
		//channel commands
		sadCommands = new ArrayList<BotCommand>();
		
		sadCommands.add(new HelpCommand());
//		sadCommands.add(new EnergyBotCommand());
		sadCommands.add(new HelloCommand());
		sadCommands.add(new WyvNetCommand());
		sadCommands.add(new IPCommand());
		sadCommands.add(new EnergyQuoteCommand());
		sadCommands.add(new CommandsCommand());
		sadCommands.add(new BordnerCommand());
		sadCommands.add(new ShadowCommand());
		

		//////////////////////
		
		BRAIN = "save\\"+botName+".ser";
		
		// load any previously saved brain
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
		
		setMessageDelay(2000);
		
		new Timer().schedule(new TimerTask() {         
		    @Override
		    public void run() {  
		    	System.out.println("Updated EnergyBot Stats.");
				userStats.updateStats(activeUsers, chatters);
				chatters.clear();
		    }
		}, 3000, 1000 * 60 * 5);
	}
	
	
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		log(message);
		
		if (!chatters.containsKey(sender)) {
			chatters.put(sender, 0);
		}
		chatters.put(sender, chatters.get(sender) + 1);
		
		message = message.trim();
		
		if (message.charAt(0) == "!".charAt(0)){
			message = message.replaceFirst("!", "");
		if (channel.equalsIgnoreCase("#activeenergylive") || channel.equalsIgnoreCase("#energybot")) {
			appendToPane(message+"\n", Color.BLACK);
			
			for(BotCommand command : sadCommands) {

				// If the message starts with the command the BotCommand responds to, remove
				// the command from the message and pass the event along to the BotCommand.
				if(message.startsWith(command.getCommandName())) {
					command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim(), mods);
					return;
				}
			}
			//TODO start
			if (message.startsWith("energybot")) {
				if (message.length() < 7) { // "sadbot ".length() = 7
					sendMessage(channel, "I am EnergyBot v" + botVersion + "! Now with coffee! Do !commands for more.");
					return;
				}
				message = message.replaceFirst("energybot ", "");
			if (message.startsWith("command") && (mods.contains(sender))) {
				if (message.length() < "command ".length()){
					sendMessage(channel, sender + ", you are derp! Try add or remove.");
					return;
				} else {
					final String[] split = message.split(" ");
					if (split[1].equals("add") && (message.length() > "command add".length())) {
//						ccommands.put("Commands", (ccommands.get("Commands") + " !"+split[2]));
						//debug
//						System.out.println(".." + ccommands.get("Commands")+ "..");
						ccommands.put(split[2], message.replace("command add "+split[2]+" ", "").trim());
						sendMessage(channel, "Command: !"+ split[2] + " > " + message.replace("command add "+split[2]+" ", "").trim());
						saveData(ccommands, "save\\energyCommands.dat");
					} else if (split[1].equals("remove") && (message.length() > "command remove".length())) {
						if (ccommands.get(split[2]) != null) {
						ccommands.remove(split[2]);
//						ccommands.put("Commands", (ccommands.get("Commands").replace("!"+split[2], " ").replace("  ", " ").trim()));
						//debug
//						System.out.println(".." + ccommands.get("Commands")+ "..");
						sendMessage(channel, "Removed command: !" + split[2]);
						saveData(ccommands, "save\\energyCommands.dat");
						} else {
							sendMessage(channel, "I cannot find the command '!" + split[2] + "' (use !command remove cake)");
						}
					} else if (split[1].equals("list")) {
//						sendMessage(channel, "Custom commands: "+ccommands.get("Commands").trim().replace(" ", ", ").replace("!", ""));
//						System.out.println(ccommands.keySet().toString());
						sendMessage(channel, "Custom commands: "+ccommands.keySet().toString().replace("[", "").replace("]", ""));
						
					} else {
						sendMessage(channel, sender + ", you are derp! [syntax error]");
					}
				}
			} else if (message.startsWith("user") && mods.contains(sender)) {
				if (message.length() < "command ".length()){
					sendMessage(channel, sender + "... try info or list");
					return;
				} else {
					String[] split = message.split(" ");
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
			}
			//sender said something we dont know 
			this.sendMessage(channel, sender + ", trust cake more. [command/user]");
			return;
			}
			//TODO end
			
			//check commands?
			if (!ccommands.isEmpty()) {
//				String[] split = ccommands.get("Commands").trim().replace("!", "").split(" ");
				final String[] split = ccommands.keySet().toArray(new String[0]);
				
			for (int i = 0; i < split.length; i++) {
				if (message.startsWith(split[i]) && (split[i].length() > 1)) {
//					System.out.println(split[i] + " and message: "+message);
					sendMessage(channel, "+ "+ccommands.get(split[i]));
					return;
				}
			}
			}
			//
		}
		//commands in all channels:
		for(BotCommand command : commands) {

			// If the message starts with the command the BotCommand responds to, remove
			// the command from the message and pass the event along to the BotCommand.
			if(message.startsWith(command.getCommandName())  && (sender.equals("shady1765") || sender.equals("activeenergylive"))) {
				command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim(), mods);
				return;
			}
		}
		//bot admin commands
		if (message.startsWith("add") && sender.equals("shady1765")) {
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
		return;
		//if message doesnt START with '!', continue
		} else {
		//END !commands
		
		
		//////////////////////BRAAAAAAAAAAAIIIIIIIIIIIIINSSSsss     (start AI)
		if(message.toLowerCase().contains("energybot")  && (!sender.equals("energybot"))) {
			if (message.contains("random")) {
				sendMessage(channel, hal.getSentence());
			}else 
		if (message.startsWith("hey") || message.contains("hello") || message.startsWith("hi")) {
			final String helloUser = hal.getSentence(sender.replace("#", "").toLowerCase());
			if (helloUser.toLowerCase().contains(sender)){
				sendMessage(channel, helloUser);
			} else {
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
			}
		} else if (message.startsWith("do") || message.contains("who") || message.contains("why") || message.contains("what") || message.contains("where") || message.contains("how") || message.contains("whose") || (message.startsWith("is")) || (message.startsWith("does"))) {
			//message is a question
			message = message.replaceAll("((e|E)nergy(b|B)ot)(, | )", "");
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

			final String[] cleanBlock = message.split(" ");

			// "sadbot, what is x?"; responds with sentence(x), AND MORE!
			String baja = "cake";
			
			for (int i=0; i<(cleanBlock.length); i++) {
				if (cleanBlock[i].length() > 3) {
					baja = cleanBlock[i];
					break;
				}
			}
			sendMessage(channel, hal.getSentence(baja));
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
			else {
				final String mesClean = message.replaceAll("(energybot)", "").replaceAll("( is | are | a | an | the | some | few | that | this | those | these | much | enough | each | every | either | neither | any | many )", " ");
				final String[] cleanBlock = mesClean.replace("?", "").replace("!", "").replace(".", "").replace("\"","").split(" ");
				final float baka = Math.round(cleanBlock.length>>1);
				int baja;
				baja=(int)baka;
				if(baja > cleanBlock.length){
					baja--;
				}
				sendMessage(channel, hal.getSentence(cleanBlock[baja]));
			}
		  	} else if(channel.equalsIgnoreCase("#activeenergylive")){
		  		
		  		
		  		//crap
				final Pattern r = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[\\-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9\\.\\-]+|(?:www\\.|[\\-;:&=\\+\\$,\\w]+@)[A-Za-z0-9\\.\\-]+)((?:\\/[\\+~%\\/\\.\\w\\-_]*)?\\??(?:[\\-\\+=&;%@\\.\\w_]*)#?(?:[\\.\\!\\/\\\\\\w]*))?)");
				final Matcher m = r.matcher(message);
				//
		  		
		  		if (!m.find()){
		  			System.err.println("saving brains :3");
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
		  		} else {
//		  			sendMessage(channel, "found: " + message);
		  			System.err.println("bad links! "+message);
		  		}
		}
		///////////////////////end AI

		}
	}
	
	@Override
	public void onJoin(String channel, String sender, String login, String hostname) {
		if (channel.equals("#activeenergylive")) {
		if (sender.equals("activeenergylive")) {
			sendMessage(channel, "hi active! :3");
		}
		if (userStats.isNew(sender)) {
			userStats.add(sender);
			System.out.println("EnergyBot: Added viewer: "+sender+" on "+userStats.lastSeen(sender)+".");
			
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
		final String[] spaz = mode.split(" "); //hint: mode is message
		if (spaz[0].equals("#activeenergylive")) {
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
	
	@Override
	public void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target){
		sendNotice(sourceNick,"\001VERSION sad_bot:"+botVersion+":Shady1765\001");
	}
	
	@Override
	public void onDisconnect(){
		appendToPane("OMG "+this.getName()+" got disconnected!\n", Color.RED);
		while (!isConnected()) {
		    try {
		        reconnect();
		       	joinChannel("#activeenergylive");
		    	Thread.sleep(100);
		    	joinChannel("#energybot");
		    	Thread.sleep(3000);
		    	sendMessage("#energybot", "im back BibleThump");
		    }
		    catch (Exception e) {
		        // Couldn't reconnect, pause for 5 secs.
		    	try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
				}
		    }
		}
	}
	
	@Override
    public void log(String line) {
			if (line.startsWith(">>>") || line.startsWith("***")) {
				if (!line.startsWith(">>>PONG")){
					appendToPane(line+"\n", Color.RED.darker());
				}
			}else{
				if (line.charAt(0) == "#".charAt(0)) {
					appendToPane(line+"\n", Color.red);
				}else
				if (!line.startsWith("PING")) {
					appendToPane(line+"\n", Color.ORANGE.darker());
				}
			}
	}
	
	private void appendToPane(String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        final int len = newGUI.tPane.getDocument().getLength();
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
