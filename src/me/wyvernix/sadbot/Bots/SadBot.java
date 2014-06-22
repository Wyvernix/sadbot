package me.wyvernix.sadbot.Bots;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import me.wyvernix.sadbot.newGUI;
import me.wyvernix.sadbot.Commands.BotCommand;
import me.wyvernix.sadbot.Commands.CommandsCommand;
import me.wyvernix.sadbot.Commands.HelloCommand;
import me.wyvernix.sadbot.Commands.HelpCommand;
import me.wyvernix.sadbot.Commands.IPCommand;
import me.wyvernix.sadbot.Commands.JoinCommand;
import me.wyvernix.sadbot.Commands.LeaveCommand;
import me.wyvernix.sadbot.Commands.LinkFilterCommand;
import me.wyvernix.sadbot.Commands.QuoteCommand;
import me.wyvernix.sadbot.Commands.RaffleCommand;
import me.wyvernix.sadbot.Commands.TweetCommand;
import me.wyvernix.sadbot.Commands.WinnerCommand;
import me.wyvernix.sadbot.Commands.WyvNetCommand;
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
		sadCommands.add(new QuoteCommand("sadQ.dat"));
		sadCommands.add(new WinnerCommand());
//		sadCommands.add(new PermitCommand());
		sadCommands.add(new LinkFilterCommand());
		sadCommands.add(new RaffleCommand());
		sadCommands.add(new TweetCommand());
		
		List<ChatFilter> filters = new ArrayList<ChatFilter>();
		//Link filter is on by default
//		filters.add(new CapFilter());
		filters.add(new VineFilter());
//		filters.add(new SymbolFilter());
		
		setCommands(commands);
		setSadCommands(sadCommands);
		setFilters(filters);
		
		setColors(Color.BLUE.darker(), Color.GREEN.darker());
		
		timer.schedule(new TimerTask() {         
		    @Override
		    public void run() {  
		    	if (!isConnected()) {
					tryReconnect();
					sendMessage(mainChan, "stupid twitch keeps disconnecting me BibleThump");
		    	}
		    }
		}, 1000 * 60, 1000 * 60);
		
		init();
	}
	
	@Override
	protected void manageUserList(boolean mode, String user) {
		if (mode) {
			//add user
			newGUI.sbUsers.addElement(user);
			newGUI.splitPane.setResizeWeight(0.5);
		} else {
			//remove user
			newGUI.sbUsers.removeElement(user);
			newGUI.splitPane.setResizeWeight(0.5);
		}
	}
	
	@Override
	protected boolean checkLinkFilter(String channel, String sender, String message) {
		return false;
	}
}