package me.wyvernix.sadbot.Bots;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.wyvernix.sadbot.Commands.BotCommand;
import me.wyvernix.sadbot.Commands.CommandsCommand;
import me.wyvernix.sadbot.Commands.HelloCommand;
import me.wyvernix.sadbot.Commands.HelpCommand;
import me.wyvernix.sadbot.Commands.IPCommand;
import me.wyvernix.sadbot.Commands.JoinCommand;
import me.wyvernix.sadbot.Commands.LeaveCommand;
import me.wyvernix.sadbot.Commands.LinkFilterCommand;
import me.wyvernix.sadbot.Commands.RaffleCommand;
import me.wyvernix.sadbot.Commands.WyvNetCommand;
import me.wyvernix.sadbot.Commands.SadBot.SadQuoteCommand;
import me.wyvernix.sadbot.Filters.ChatFilter;
import me.wyvernix.sadbot.Filters.VineFilter;

public class SadBot extends MasterBot {
	public SadBot() {
		setBotName("Sad_Bot");
		setMainChan("#shady1765");
		//global commands
		List<BotCommand> commands = new ArrayList<BotCommand>();
		commands.add(new JoinCommand());
		commands.add(new LeaveCommand());
		//channel commands
		List<BotCommand> sadCommands = new ArrayList<BotCommand>();
		sadCommands.add(new HelpCommand());
		sadCommands.add(new HelloCommand());
		sadCommands.add(new WyvNetCommand());
		sadCommands.add(new IPCommand("shady1765"));
		sadCommands.add(new CommandsCommand());
		sadCommands.add(new SadQuoteCommand());
		sadCommands.add(new RaffleCommand());
//		sadCommands.add(new PermitCommand());
		sadCommands.add(new LinkFilterCommand());
		
		List<ChatFilter> filters = new ArrayList<ChatFilter>();
		//Link filter is on by default
//		filters.add(new CapFilter());
		filters.add(new VineFilter());
//		filters.add(new SymbolFilter());
		
		setCommands(commands);
		setSadCommands(sadCommands);
		setFilters(filters);
		
		setColors(Color.BLUE.darker(), Color.GREEN.darker());
		//Color.BLUE.darker());
		//Color.GREEN.darker()
		init();
	}
	
//	@Override
//	protected boolean checkLinkFilter(String channel, String sender, String message) {
//		return false;
//	}
}





//package me.wyvernix.sadbot.Bots;
//
//import java.awt.Color;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutput;
//import java.io.ObjectOutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.swing.text.AttributeSet;
//import javax.swing.text.SimpleAttributeSet;
//import javax.swing.text.StyleConstants;
//import javax.swing.text.StyleContext;
//
//import me.wyvernix.sadbot.Commands.*;
//import me.wyvernix.sadbot.Commands.SadBot.SadQuoteCommand;
//import me.wyvernix.sadbot.UserStats;
//import me.wyvernix.sadbot.newGUI;
//
//import org.jibble.pircbot.*;
//import org.jibble.jmegahal.JMegaHal;
//
//
//public class SadBot extends PircBot {
//	private static final String botVersion = "2.3.3";
//	private static final String botName = "Sad_Bot";
////	private ArrayList<String> saveData = new ArrayList<String>();
//	
//	private ArrayList<String> mods = new ArrayList<String>();
//	private ArrayList<String> activeUsers = new ArrayList<String>();
//	public UserStats userStats = new UserStats(botName);
//	private Map<String, Integer> chatters = new HashMap<String, Integer>(); 
//	
////	private static GUI currentGUI;
//	
////	private boolean follows = false;
////	Timer timer;
//	private static String BRAIN = "save\\brain.ser"; //$NON-NLS-1$
//	JMegaHal hal = new JMegaHal();
//	
//	
//	
//	private void firstRun() {
//		System.err.println("Couldn't find the brain ("+ BRAIN +") so will use default data");
//		hal.add("Hello World");
//		hal.add("Can I have some coffee?");
//		hal.add("Please slap me");
//		try {
//			hal.addDocument("https://dl.dropboxusercontent.com/u/26842546/getsmart.txt");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	
//	////////////
//	private final List<BotCommand> commands;
//	private final List<BotCommand> sadCommands;
//	private Map<String,String> ccommands = new HashMap<String,String>();
//	/////////////
//	
//	@SuppressWarnings("unchecked")
//	public SadBot() {
//		System.out.println("Starting " + botName + ".");
//		
//		linkPatterns[0] = Pattern.compile(".*http://.*", Pattern.CASE_INSENSITIVE);
//        linkPatterns[1] = Pattern.compile(".*https://.*", Pattern.CASE_INSENSITIVE);
//        linkPatterns[2] = Pattern.compile(".*[-A-Za-z0-9]+\\s?(\\.|\\(dot\\))\\s?(ac|ad|ae|aero|af|ag|ai|al|am|an|ao|aq|ar|as|asia|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|bi|biz|bj|bm|bn|bo|br|bs|bt|bv|bw|by|bz|ca|cat|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|co|com|coop|cr|cu|cv|cw|cx|cy|cz|de|dj|dk|dm|do|dz|ec|edu|ee|eg|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gl|gm|gn|gov|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|in|info|int|io|iq|ir|is|it|je|jm|jo|jobs|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mil|mk|ml|mm|mn|mo|mobi|mp|mq|mr|ms|mt|mu|museum|mv|mw|mx|my|mz|na|name|nc|ne|net|nf|ng|ni|nl|no|np|nr|nu|nz|om|org|pa|pe|pf|pg|ph|pk|pl|pm|pn|post|pr|pro|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sx|sy|sz|tc|td|tel|tf|tg|th|tj|tk|tl|tm|tn|to|tp|tr|travel|tt|tv|tw|tz|ua|ug|uk|us|uy|uz|va|vc|ve|vg|vi|vn|vu|wf|ws|xxx|ye|yt|za|zm|zw)(\\W|$).*", Pattern.CASE_INSENSITIVE);
//        linkPatterns[3] = Pattern.compile(".*(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\\s+|:|/|$).*");
//
//        symbolsPatterns[0] = Pattern.compile("(\\p{InPhonetic_Extensions}|\\p{InLetterlikeSymbols}|\\p{InDingbats}|\\p{InBoxDrawing}|\\p{InBlockElements}|\\p{InGeometricShapes}|\\p{InHalfwidth_and_Fullwidth_Forms}|つ|°|ຈ|░|▀|▄|̰̦̮̠ę̟̹ͦͯͯ́ͮ̊̐͌̉͑ͨ̊́́̚|U̶̧ͩͭͧ͊̅̊ͥͩ̿̔̔ͥ͌ͬ͊͋ͬ҉|Ọ̵͇̖̖|A̴͍̥̳̠̞̹ͩ̋̆ͤͅ|E̡̛͚̺̖̪͈̲̻̠̰̳̐̿)");
//        symbolsPatterns[1] = Pattern.compile("[!-/:-@\\[-`{-~]");
//		
//		//////////////////////////
//		//TODO: commands
//		//load ccommands
////		ccommands.put("Commands", "lol");
//		ObjectInputStream inp = null;
//		final String commandsFile= "save\\sadCommands.dat";
//		try {
//			final File file = new File(commandsFile);
//		    inp = new ObjectInputStream(new FileInputStream(file));
//		    ccommands = (Map<String,String>) inp.readObject();
//		} catch (FileNotFoundException e) {
//			System.err.println("Couldn't find the data ("+ commandsFile +") so will fix?");
//			saveData(ccommands, commandsFile);
//		} catch (IOException e) {
//			System.err.println("Couldn't find the data ("+ commandsFile +") so will fix?");
//			saveData(ccommands, commandsFile);
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} finally {
//			if(inp != null) {
//				try {
//					inp.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//		
//		
//		
//		
//		//global commands
//		commands = new ArrayList<BotCommand>();
//		
//		commands.add(new JoinCommand());
//		commands.add(new LeaveCommand());
//		
//		//channel commands
//		sadCommands = new ArrayList<BotCommand>();
//		
//		sadCommands.add(new HelpCommand());
////		sadCommands.add(new SadBotCommand(botVersion));
//		sadCommands.add(new HelloCommand());
//		sadCommands.add(new WyvNetCommand());
//		sadCommands.add(new IPCommand());
//		sadCommands.add(new CommandsCommand());
//		sadCommands.add(new SadQuoteCommand());
//		
//		//////////////////////
//		
//		BRAIN = "save\\"+botName+".ser";
//		
//		// load any previously saved brain
//		ObjectInputStream in = null;
//		try {
//			final File file = new File(BRAIN);
//		    in = new ObjectInputStream(new FileInputStream(file));
//			hal = (JMegaHal) in.readObject();
//		} catch (FileNotFoundException e) {
//			firstRun();
//		} catch (IOException e) {
//			firstRun();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} finally {
//			if(in != null) {
//				try {
//					in.close();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
//			}
//		}
//
//		this.setName(botName);
//		this.setVersion("Sad_Bot v"+botVersion); // visible to whois and CTCP version
//		this.setFinger("Get your hand off of me!");  // visible on CTCP finger
//		
//		setMessageDelay(2000);
//		
//		
//		//chan data?
//		////////////////////
////		if (this.getName()=="sad_bot") {
////		final String flie = "save\\fun."+botName+".dat";
////		ObjectInputStream inp = null;
////		try {
////			File file = new File(flie);
////		    inp = new ObjectInputStream(new FileInputStream(file));
////		    saveData = (ArrayList<String>) inp.readObject();
////		} catch (FileNotFoundException e) {
////			System.err.println("Couldn't find the data ("+ flie +") so will fix?");
////			saveData(saveData, flie);
////		} catch (IOException e) {
////			System.err.println("Couldn't find the data ("+ flie +") so will fix?");
////			saveData(saveData, flie);
////		} catch (ClassNotFoundException e) {
////			e.printStackTrace();
////		} finally {
////			if(inp != null) {
////				try {
////					inp.close();
////				} catch (IOException e1) {
////					e1.printStackTrace();
////				}
////			}
////		}
////		
////		new Timer().schedule(new TimerTask() {         
////		    @Override
////		    public void run() {  
//////		    	System.err.println("Saving sadbot data...");
//////				saveData(saveData, flie);
//////				appendToPane("Saved SadBot data."+System.getProperty("line.separator"), Color.BLACK);
//////		    }
//////		}, 3000, 1000 * 60 * 5);
////		
////		
////		} else {
////			final String fliee = "save\\fun."+botName+".dat";
////			ObjectInputStream inp = null;
////			try {
////				File file = new File(fliee);
////			    inp = new ObjectInputStream(new FileInputStream(file));
////			    saveData = (ArrayList<String>) inp.readObject();
////			} catch (FileNotFoundException e) {
////				System.err.println("Couldn't find the data ("+ fliee +") so will fix?");
////				saveData(saveData, fliee);
////			} catch (IOException e) {
////				System.err.println("Couldn't find the data ("+ fliee +") so will fix?");
////				saveData(saveData, fliee);
////			} catch (ClassNotFoundException e) {
////				e.printStackTrace();
////			} finally {
////				if(inp != null) {
////					try {
////						inp.close();
////					} catch (IOException e1) {
////						e1.printStackTrace();
////					}
////				}
////			}
//////			
//////			new Timer().schedule(new TimerTask() {         
//////			    @Override
//////			    public void run() {  
//////			    	System.err.println("Saving energybot data...");
//////					saveData(saveData, fliee);
//////					appendToPane("Saved EnergyBot data."+System.getProperty("line.separator"), Color.BLACK);
//////			    }
//////			}, 3000, 1000 * 60 * 5);
////		}
//		//////////////////
//		
//		
//		
////        TimedMessageTask tmt1 = new TimedMessageTask("save");
////
////        timer = new Timer();
////        timer.schedule(tmt1, 0, 30 * 1000); // 30 secs
////        timer.schedule(tmt1, 0, 20 * 60 * 1000);  // 15 min
//		
//		
//		new Timer().schedule(new TimerTask() {         
//		    @Override
//		    public void run() {  
//		    	System.out.println("Updated SadBot Stats.");
//				userStats.updateStats(activeUsers, chatters);
//				chatters.clear();
////				appendToPane("Updated SadBot Stats."+System.getProperty("line.separator"), Color.BLACK);
//		    }
//		}, 3000, 1000 * 60 * 5);
//	}
//	
//	
//	@Override
//	public void onMessage(String channel, String sender, String login, String hostname, String message) {
//		log(message);
//		
//		message = message.trim();
//		
//		if (message.charAt(0) == "!".charAt(0)){
//			message = message.replaceFirst("!", "");
//		if (channel.equalsIgnoreCase("#shady1765") || channel.equalsIgnoreCase("#sad_bot")) {
//			appendToPane(message+""+System.getProperty("line.separator"), Color.BLACK);
//			
//			for(BotCommand command : sadCommands) {
//
//				// If the message starts with the command the BotCommand responds to, remove
//				// the command from the message and pass the event along to the BotCommand.
//				if(message.startsWith(command.getCommandName())) {
//					command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim(), mods);
//					return;
//				}
//			}
//			
//			//sadbot command start
//			if (message.startsWith("sadbot")) {
//				if (message.length() < 7) { // "sadbot ".length() = 7
//					sendMessage(channel, "I am Sad_Bot v" + botVersion + "! Now with coffee! Do !commands for more.");
//					return;
//				}
//				message = message.replaceFirst("sadbot ", "");
//			if (message.startsWith("command") && (mods.contains(sender))) {
//				
//				if (message.length() < "command ".length()){
//					sendMessage(channel, sender + ", you are derp! Try add or remove");
//					return;
//				} else {
//					final String[] split = message.split(" ");
//					if (split[1].equals("add") && (message.length() > "command add".length())) {
////						ccommands.put("Commands", (ccommands.get("Commands") + " !"+split[2]));
//						//debug
////						System.out.println(".." + ccommands.get("Commands")+ "..");
//						ccommands.put(split[2], message.replace("command add "+split[2]+" ", "").trim());
//						sendMessage(channel, "Command: !"+ split[2] + " > " + message.replace("command add "+split[2]+" ", "").trim());
//						saveData(ccommands, "save\\sadCommands.dat");
//						return;
//					} else if (split[1].equals("remove") && (message.length() > "command remove".length())) {
//						if (ccommands.get(split[2]) != null) {
//						ccommands.remove(split[2]);
////						ccommands.put("Commands", (ccommands.get("Commands").replace("!"+split[2], " ").replace("  ", " ").trim()));
//						//debug
////						System.out.println(".." + ccommands.get("Commands")+ "..");
//						sendMessage(channel, "Removed command: !" + split[2]);
//						saveData(ccommands, "save\\sadCommands.dat");
//						return;
//						} else {
//							sendMessage(channel, "I cannot find the command '!" + split[2] + "' (use !command remove cake)");
//							return;
//						}
//					} else if (split[1].equals("list")) {
////						sendMessage(channel, "Custom commands: "+ccommands.get("Commands").trim().replace(" ", ", ").replace("!", ""));
////						System.out.println(ccommands.keySet().toString());
//						sendMessage(channel, "Custom commands: "+ccommands.keySet().toString().replace("[", "").replace("]", ""));
//						return;
//					} else {
//						sendMessage(channel, sender + ", you are derp! [syntax error]");
//						return;
//					}
//				}
//			} else if (message.startsWith("user") && mods.contains(sender)) {
//				if (message.length() < "command ".length()){
//					sendMessage(channel, sender + "... try info or list");
//					return;
//				} else {
//					final String[] split = message.split(" ");
//					split[2] = split[2].toLowerCase();
//					if (split[1].equals("info") && (message.length() > "user info ".length())) {
//						final Map<String,Object> userData = userStats.users.get(split[2]);
//						if (userData != null) {
//						this.sendMessage(channel, split[2]+": " + 
//								Integer.toString((int)userData.get("viewTime")*5) + " minutes watched, " + 
//								Integer.toString((int)userData.get("chatLines")) + " chat lines, " + 
//								Integer.toString((int)userData.get("contestWins")) + " contest wins, and was last seen " 
//								+userStats.lastSeen(split[2]));
//						return;
//						} else {
//							this.sendMessage(channel, "User: "+split[2]+" not found.");
//							return;
//						}
//					} else if (split[1].equals("list")) {
//						this.sendMessage(channel, userStats.users.keySet().toString());
//						return;
//					}
//				}
//			}
//			//sender said something we dont know 
//			this.sendMessage(channel, sender + ", trust cake more. [command/user]");
//			return;
//			}
//			//TODO command end
//			
//			//check commands?
//			if (!ccommands.isEmpty()) {
////				String[] split = ccommands.get("Commands").trim().replace("!", "").split(" ");
//				final String[] split = ccommands.keySet().toArray(new String[1]);
//				
//			for (int i = 0; i < split.length; i++) {
//				if (message.startsWith(split[i]) && (split[i].length() > 1)) {
////					System.out.println(split[i] + " and message: "+message);
//					sendMessage(channel, "+ "+ccommands.get(split[i]));
//					return;
//				}
//			}
//			}
//			
//			/////////////
//		}
//		//commands in all channels:
//		for(BotCommand command : commands) {
//
//			// If the message starts with the command the BotCommand responds to, remove
//			// the command from the message and pass the event along to the BotCommand.
//			if(message.startsWith(command.getCommandName()) && (sender.equals("shady1765"))) {
//				command.handleMessage(this, channel, sender, message.replace(command.getCommandName(), "").trim(), mods);
//				return;
//			}
//		}
//		//bot admin commands
//		if (message.startsWith("add") && sender.equals("shady1765")) {
//			try {
//				hal.addDocument("https://dl.dropboxusercontent.com/u/26842546/getsmart.txt");
//  				// save the new data
//  				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
//  				out.writeObject(hal);
//  				out.close();
//  				sendMessage(channel, "Nom nom words :3");
//  			} catch (FileNotFoundException e) {
//  				e.printStackTrace();
//  			} catch (IOException e) {
//  				e.printStackTrace();
//  			}
//			return;
//		}
//		return;
//		//if message doesnt START with '!', continue
//		} else {
//		//END !commands
//			if (!chatters.containsKey(sender)) {
//				chatters.put(sender, 0);
//			}
//			chatters.put(sender, chatters.get(sender) + 1);
//		
//		//////////////////////BRAAAAAAAAAAAIIIIIIIIIIIIINSSSsss     (start AI)
//		
//			
//			
//			//TODO check spam
//			boolean isSpam = checkSpam(channel, sender, message);
//			
//			
//			
//		if((message.toLowerCase().contains("sadbot") || message.toLowerCase().contains("sad_bot")) && (!sender.equals("sad_bot"))) {
//			
//			respondAI(channel, message, sender);
//		  	}
//		
//		else if(channel.equalsIgnoreCase("#shady1765")){
//		  		
//		  		
//		  		//crap
//				Pattern rer = Pattern.compile("((([A-Za-z]{3,9}:(?:\\/\\/)?)(?:[\\-;:&=\\+\\$,\\w]+@)?[A-Za-z0-9\\.\\-]+|(?:www\\.|[\\-;:&=\\+\\$,\\w]+@)[A-Za-z0-9\\.\\-]+)((?:\\/[\\+~%\\/\\.\\w\\-_]*)?\\??(?:[\\-\\+=&;%@\\.\\w_]*)#?(?:[\\.\\!\\/\\\\\\w]*))?)");
//				Matcher match = rer.matcher(message);
//				//
//		  		
//		  		if (!match.find()){
//		  			// add the new data to the brain
////		  			sendMessage(channel, "not found: " + message);
//		  			hal.add(message.replace("\"", ""));
//		  			try {
//		  				// save the new data
//		  				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
//		  				out.writeObject(hal);
//		  				out.close();
//		  			} catch (FileNotFoundException e) {
//		  				e.printStackTrace();
//		  			} catch (IOException e) {
//		  				e.printStackTrace();
//		  			}
//		  		}
////		  		else {
////		  			sendMessage(channel, "found: " + message);
////		  		}
//		}
//		///////////////////////end AI
//
//		}
//		
//	}
//	
//	
//	//TODO//////////
//	private Pattern[] linkPatterns = new Pattern[4];
//    private Pattern[] symbolsPatterns = new Pattern[2];
////    private Pattern banNoticePattern = Pattern.compile("^You are permanently banned from talking in ([a-z_]+).$", Pattern.CASE_INSENSITIVE);
////    private Pattern toNoticePattern = Pattern.compile("^You are banned from talking in ([a-z_]+) for (?:[0-9]+) more seconds.$", Pattern.CASE_INSENSITIVE);
//    private Pattern vinePattern = Pattern.compile(".*(vine|4).*(4|vine).*Google.*", Pattern.CASE_INSENSITIVE);
//	
//	private boolean checkSpam(String channel, String sender, String message) {
//		
//		
//		return false;
//	}
//
//	private boolean containsLink(String message) {
//        String[] splitMessage = message.toLowerCase().split(" ");
//        for (String m : splitMessage) {
//            for (Pattern pattern : linkPatterns) {
//                //System.out.println("Checking " + m + " against " + pattern.pattern());
//                Matcher match = pattern.matcher(m);
//                if (match.matches()) {
//                    log("RB: Link match on " + pattern.pattern());
//                    return true;
//                }
//            }
//        }
////        for (Pattern pattern : linkPatterns) {
////            System.out.println("Checking " + message + " against " + pattern.pattern());
////            Matcher match = pattern.matcher(message);
////            if (match.matches()) {
////                System.out.println("Bypass match");
////                return true;
////            }
////        }
//        return false;
//    }
//
//	
//	/////////////////////
//	
//	private void respondAI(String channel, String message, String sender) {
//		if (message.contains("random")) {
//			sendMessage(channel, hal.getSentence());
//		}else 
//	if (message.startsWith("hey") || message.contains("hello") || message.startsWith("hi")) {
////		String helloUser = hal.getSentence(sender.replace("#", "").toLowerCase());
////		if (helloUser.toLowerCase().contains(sender)){
////			sendMessage(channel, helloUser);
////		} else {
//			sendMessage(channel, "Hello " + sender.toLowerCase() + "!");
//			
////			hal.add("Hello again, " + sender.replace("#", "").toLowerCase() + "!");
////			try {
////	    		// save the new data
////		    	ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
////				out.writeObject(hal);
////		        out.close();
////			} catch (FileNotFoundException e) {
////				e.printStackTrace();
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////		}
//	} else if (message.startsWith("do") || message.contains("who") || message.contains("why") || message.contains("what") || message.contains("where") || message.contains("how") || message.contains("whose") || (message.startsWith("is")) || (message.startsWith("does"))) {
//		//message is a question
//		message = message.replaceAll("((s|S)ad(b|B)ot|(s|S)ad_(b|B)ot)(, | )", "");
//		if (message.startsWith("why") || message.startsWith("Why")) {
//			sendMessage(channel, hal.getSentence("Because"));
//
//		} else if (message.contains("do") || message.contains("does")) {
//			if (Math.random() <0.5){
//				sendMessage(channel, "yes");	
//			} else {
//				sendMessage(channel, "no");
//			}
//		}else{
//			
//		message = message.replaceAll("(do|who|why|what|where|how|whose|do|does|like)(,| |\\?)", "").replace("?", "").replace(".", "").replace("!", "").replace(",", "")+" cake";
//
//		String[] cleanBlock = message.split(" ");
//
//		// "sadbot, what is x?"; responds with sentence(x), AND MORE!
//		String baja = "cake";
//		
//		for (int i=0; i<(cleanBlock.length); i++) {
//			if (cleanBlock[i].length() > 3) {
//				baja = cleanBlock[i];
//				break;
//			}
//		}
//		
//
//		sendMessage(channel, hal.getSentence(baja));
//
////		else {
////			float baka = Math.round(cleanBlock.length/2);
////			int baja;
////			baja=(int)baka;
////			if(baja > cleanBlock.length){
////				baja = baja-1;
////			}
////			sendMessage(channel, hal.getSentence(cleanBlock[baja]));
////		}
//		}
//	} else if (message.startsWith("are you")) {
//		if (Math.random() <0.5){
//			sendMessage(channel, "yes");	
//		} else {
//			if (Math.random() >0.1){
//			sendMessage(channel, "no");
//			} else {
//				sendMessage(channel, "i am cake :3");
//			}
//		}
//	}
////	else if (message.contains("sadbot is ") || message.contains("sad_bot is ") || message.contains("energybot is ")) {
////		sendMessage(channel, hal.getSentence("sadbot").replaceAll("sadbot is", "I am") + " Kappa");
////		
////		hal.add(message.replaceAll("sad_bot", "sadbot").replaceAll("energybot", "sadbot"));
////		try {
////    		// save the new data
////	    	ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
////			out.writeObject(hal);
////	        out.close();
////		} catch (FileNotFoundException e) {
////			e.printStackTrace();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////	} else if (message.contains("what is sadbot") || message.contains("what is sad_bot")) {
////		sendMessage(channel, hal.getSentence("sadbot") + " Kappa");
////	} else if (message.contains("what is energybot")) {
////		sendMessage(channel, hal.getSentence("sadbot").replaceAll("sadbot", "energybot") + " Kappa");
////	}
//	else {
//		String mesClean = message.replaceAll("(sadbot|sad_bot)", "").replaceAll("( is | are | a | an | the | some | few | that | this | those | these | much | enough | each | every | either | neither | any | many )", " ");
//		String[] cleanBlock = mesClean.replace("?", "").replace("!", "").replace(".", "").replace("\"","").split(" ");
//		float baka = Math.round(cleanBlock.length>>1);
//		int baja;
//		baja=(int)baka;
//		if(baja > cleanBlock.length){
//			baja--;
//		}
//		sendMessage(channel, hal.getSentence(cleanBlock[baja]));
//	}		
//	}
//
//
//	
//	@Override
//	public void onJoin(String channel, String sender, String login, String hostname) {
//		if (channel.equals("#shady1765")) {
//		if (sender.equals("shady1765")) {
//			sendMessage(channel, "hi shady! :3");
//		}
//		
//		if (userStats.isNew(sender)) {
//			userStats.add(sender);
//			System.out.println("SadBot: Added viewer: "+sender+" on "+userStats.lastSeen(sender)+".");
//			
//		} else {
//			userStats.updateLastSeen(sender);
//		}
//		if (!activeUsers.contains(sender)) {
//			activeUsers.add(sender);
//		}
//		}
//	}
//	
//	@Override
//	public void onPart(String channel, String sender, String login, String hostname) {
//		if (activeUsers.contains(sender)) {
//			activeUsers.remove(sender);
//			userStats.updateLastSeen(sender);
//		}
//	}
//	
//	
//	@Override
//	public void onUserMode(String targetNick, String sourceNick, String sourceLogin, String sourceHostname, String mode) {
//		//Manage mods list
//		String[] spaz = mode.split(" ");
//		if (spaz[0].equals("#shady1765")) {
//		if (spaz[1].equals("+o")){
//			if (!mods.contains(spaz[2])) {
//				mods.add(spaz[2]);
//			}
//		} else if (spaz[1].equals("-o")) {
//			if (mods.contains(spaz[2])) {
//				mods.remove(spaz[2]);				
//			}
//		}
//		}
//	}
//	
////	class TimedMessageTask extends TimerTask {
////        private String message = "";
////        
////        public TimedMessageTask(String message) {
////        	if (message.contains("hal")){
////        		this.message = hal.getSentence("cake");
//////        	} else if (message.contains("save")) {
//////        		try {
//////	  				// save the new data
//////	  				ObjectOutput out = new ObjectOutputStream(new FileOutputStream(BRAIN));
//////	  				out.writeObject(hal);
//////	  				out.close();
//////	  			} catch (FileNotFoundException e) {
//////	  				e.printStackTrace();
//////	  			} catch (IOException e) {
//////	  				e.printStackTrace();
//////	  			}
////        	} else {
////            this.message = message;
////        	}
////        }
////        
////        public void run() {
////        	String[] channels = getChannels();
////        	for(int x=0; x < channels.length; x++) {
////        		sendMessage(channels[x], message);
////        	}
////        }
////        
////        @Override
////        public String toString() {
////			return null;
////        	
////        }
////    }
//	
//
//	
//	@Override
//	public void onVersion(String sourceNick, String sourceLogin, String sourceHostname, String target){
//		sendNotice(sourceNick,"\001VERSION sad_bot:"+botVersion+":Shady1765\001");
//	}
//	
//	@Override
//	public void onDisconnect(){
//		appendToPane("OMG "+this.getName()+" got disconnected!"+System.getProperty("line.separator"), Color.RED);
//		boolean notConnected = false;
//		int delay = 1;
//		while (notConnected) {
//		    try {
//		    	Thread.sleep(300*delay);
//				reconnect();
//				Thread.sleep(300);
//				notConnected = !isConnected();
//				if (delay < 200) {
//					delay++;
//				}
//		    }
//		    catch (Exception e) {
//		        // Couldn't reconnect!
//		        // Pause for 5 secs
//		    	try {
//					Thread.sleep(5000);
//				} catch (InterruptedException e1) {
//					Thread.currentThread().interrupt();
//				}
//		    }
//		}
//
//		joinChannel("#sad_bot");
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//    	joinChannel("#shady1765");
//    	try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//    	sendMessage("#sad_bot", "im back BibleThump");
//	}
//	
//	@Override
//    public void log(String line) {
//			if (line.startsWith(">>>") || line.startsWith("***")) {
////				if (line.startsWith(">>>PASS")) {
////					appendToPane(">>>PASS oauth:***"+System.getProperty("line.separator"), Color.BLUE.darker());
////				}else 
//				if (!line.startsWith(">>>PONG")){
//					appendToPane(line+"\n", Color.BLUE.darker());
//				}
//			}else{
//				if (line.charAt(0) == "#".charAt(0)) {
//					appendToPane(line+"\n", Color.red);
//				}else
//				if (!line.startsWith("PING")){
//					appendToPane(line+"\n", Color.GREEN.darker());
//				}
//			}
//	}
//	
//	private void appendToPane(String msg, Color c)
//    {
//        StyleContext sc = StyleContext.getDefaultStyleContext();
//        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
//
//        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
//        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
//
//        int len = newGUI.tPane.getDocument().getLength();
//        newGUI.tPane.setCaretPosition(len);
//        newGUI.tPane.setCharacterAttributes(aset, false);
//        newGUI.tPane.replaceSelection(msg);
//    }
//	
//	public void saveData(Object saveData2, String fileName) {
//		try {
//			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
//			out.writeObject(saveData2);
//			out.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public void leaveServer() throws InterruptedException {
//		if (this.isConnected()) {
//		Thread.sleep(100);
//		this.disconnect();
//		}
//	}
//}
