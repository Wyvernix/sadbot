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
import me.wyvernix.sadbot.Commands.WyvNetCommand;
import me.wyvernix.sadbot.Commands.SadBot.SadQuoteCommand;

public class TestBot extends MasterBot {
	public TestBot() {
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
		sadCommands.add(new IPCommand());
		sadCommands.add(new CommandsCommand());
		sadCommands.add(new SadQuoteCommand());
		
		setCommands(commands);
		setSadCommands(sadCommands);
		
		setColors(Color.BLUE.darker(), Color.GREEN.darker());
		//Color.BLUE.darker());
		//Color.GREEN.darker()
		init();
	}
	
	
}
