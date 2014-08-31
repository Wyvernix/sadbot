package me.wyvernix.sadbot.Bots;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.wyvernix.sadbot.newGUI;
import me.wyvernix.sadbot.Commands.*;
import me.wyvernix.sadbot.Filters.CapFilter;
import me.wyvernix.sadbot.Filters.ChatFilter;
import me.wyvernix.sadbot.Filters.SymbolFilter;
import me.wyvernix.sadbot.Filters.VineFilter;

public class EnergyBot extends MasterBot {
	public EnergyBot(String oauth) {
		setBotName("EnergyBot");
		setMainChan("#activeenergylive");
		//global commands
		List<BotCommand> commands = new ArrayList<BotCommand>();
		commands.add(new JoinCommand());
		commands.add(new LeaveCommand());
		//channel commands
		List<BotCommand> sadCommands = new ArrayList<BotCommand>();
		sadCommands.add(new WyvNetCommand());
		sadCommands.add(new IPCommand("activeenergylive", "EnergyBot"));
		sadCommands.add(new CommandsCommand());
		sadCommands.add(new QuoteCommand("energyQ.dat"));
		sadCommands.add(new WinnerCommand());
		sadCommands.add(new PermitCommand());
		sadCommands.add(new LinkFilterCommand());
		sadCommands.add(new RaffleCommand());
		sadCommands.add(new TweetCommand());
		sadCommands.add(new BitrateCommand());
		sadCommands.add(new ViewersCommand());
		sadCommands.add(new PollCommand());
		
		List<ChatFilter> filters = new ArrayList<ChatFilter>();
		//Link filter is on by default
		filters.add(new CapFilter());
		filters.add(new VineFilter());
		filters.add(new SymbolFilter());
		
		setCommands(commands);
		setSadCommands(sadCommands);
		setFilters(filters);
		
		setColors(Color.RED.darker(), Color.ORANGE.darker());
		
		init(oauth);
	}
	
	@Override
	protected synchronized void manageUserList(boolean mode, String user) {
		if (mode) {
			//add user
			newGUI.ebUsers.addElement(user);
			newGUI.splitPane.setResizeWeight(0.5);
		} else {
			//remove user
			if (user.equals("*")) {
				newGUI.ebUsers.removeAllElements();
			} else {
				newGUI.ebUsers.removeElement(user);
			}
			newGUI.splitPane.setResizeWeight(0.5);
		}
	}
}