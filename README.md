Sad_Bot
======

Chat bot for Twitch.tv

Features:

  * Bot can manage a modlist. Sometimes Twitch eats join messages, any mods won't be able to do anything.
  * Bot can Auto-Reconnect.
  * Has GUI.
  * Can do global bans

ChangeLog is in changelog.txt

****

To Use:
-------
1. Look at the SadBot.java or EnergyBot.java class.
2. Delete one, and edit the other to fill your needs.
3. Fix BotManager.java class
4. (optional) edit newGUI.java to change name of window 


****

Commands
--------

**!help**

>"lurk more."

**!hello**

>"Welcome to the channel! Follow or die! R)"

**!wyvnet**

>"Build server: wyv.mcph.co [1.7.4]"

**!raffle**

Returns random user in channel as winner. Removes mods from list. If use "mods" modifier, it will include mods in raffle.

**!ip**

Triggers: [hypixel] [hive] [mineplex] [single, ssp]

Will return related ip, or WyvNet if none found.

Checks title of broadcast for triggers.

Automatically checks every 5 minutes, and on command. Will correct itself.

**!commands**

>"Bot commands: !hello, !ip, !help, !wyvnet, !quote, !command, and some secret ones."

**!permit**

Will permit a user against one occurrence of a ban.

**!quote**

"!quote" will return a random quote.

"!quote add (quote)" will add quote to quote list.

"!quote remove (phrase)" will search for phrase, and will remove quote if found.

"!quote list" will list all quotes to log file. DEBUG

"!quote (quote)" will add quote to quote list.

**!(botname)**

"!(botname)" > "I am <botName> v<botVersion>! Now with coffee! Do !commands for more."

"!(botname) command [add/remove/list]" manages commands. ~*Fancy*~

"!(botname) user [info/list]" gives user info. Try to only use list in bot's channel.

"!(botname) permit (user)" will permit a user to one link?

****

Filters
-------

Triggers are hard-coded for now. I will work on getting a setter working later. The tempban/warn system will warn on first trigger, then add 1 minute every time.

**CapFilter**

More then 15 chars to trigger. If more than 60% caps, will tempban/warn.

**LinkFilter**

Will tempban. No warning.

Matches: "http://" "https://" .whatever and IPs.

**SymbolFilter**

More then 3 chars to trigger. If more then 40% symbols, will tempban/warn.

**VineFilter**

Will globally instaban matches of "(vine|4) (4|vine) Google"

****

AI
--

The AI is not an AI. It is a sentence generator triggered by mentioning the bot's name.

Non-random triggers:

  * hey, hello, hi
  * do, who, why, what, where, how, whose, is, does
  * do, does
  * are you

AI will add any messages that are not command/spam to its database. AI builds sentences from database.

