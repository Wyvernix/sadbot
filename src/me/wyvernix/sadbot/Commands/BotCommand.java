package me.wyvernix.sadbot.Commands;

import java.util.ArrayList;

import org.jibble.pircbot.PircBot;

/**
 * This is the definition for all BotCommands. Anything
 * defined here must be implemented by a BotCommand.
 *
 * This specifies a method to be called on events and
 * a method to of determining the "name" of the command.
 * A more complex BotCommand could define other methods
 * to handle more events than the simple message event.
 *
 * @author AMcBain ( http://www.asmcbain.net/ ) @ 2009
 */
public interface BotCommand {

	// Each BotCommand implementor will return the command name to which they respond
	public String getCommandName();

	// The method where each BotCommand implementor will handle the event
	public void handleMessage(PircBot bot, String channel, String sender, String message, ArrayList<String> mods);

}
