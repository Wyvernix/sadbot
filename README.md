Sad_Bot
======

Chat bot for Twitch.tv

Features:

  * Bot can manage a list of mods and active chatters.
  * Bot can Auto-Reconnect. If it feels like it.
  * Has GUI.
  * Can do global bans (not really)
  * Giveaway commands
  * Chat filters
  * Basic response AI
  * Click to tweet

[Best SadBot Quotes](https://dl.dropboxusercontent.com/u/26842546/best%20sadbot%20quotes.txt "Best SadBot Quotes")

**Libraries:**

- PircBot
- jsoup 1.7.3+
- JMegaHal
- gson 2.2.4+

****

Commands
--------

**!help**

>"lurk more."

**!hello**

>"Welcome to the channel! Follow or die! R)"

**!wyvnet**

>"Build server: wyv.mcph.co [1.7.4]"

**!winner**

Returns random user in channel as winner. Removes mods from list. If use "mods" modifier, it will include mods in raffle.

**!raffle**

"!raffle" Enters raffle (is its open)

"!raffle (open | close)" whatever

**!ip**

"add [trigger] [ip]" Adds trigger and IP to IP list.

"remove [trigger]" Removes trigger and IP from list.

"list" Lists triggers.

"[trigger]" If it can find it, will return related IP.  

"" Will return related IP, or WyvNet if none found.

Checks title of broadcast for triggers.

Automatically checks every 5 minutes, and on command. Will correct itself.

If you want multiple triggers, do "trigger1.*?trigger2", replacing trigger1 and trigger2. (Uses regex, but don't use spaces) 

**!commands**

>"Bot commands: !hello, !ip, !help, !wyvnet, !quote, and some secret ones. Check the wiki."

**!permit**

Will permit a user against one occurrence of a ban. Times out after 5 minutes.

**!quote**

"!quote" will return a random quote.

"!quote add [quote]" will add quote to quote list.

"!quote remove (phrase | #)" will search for phrase, and will remove quote if found. If number given, will remove numbered quote.

"!quote list" will list all quotes to log file. DEBUG

"!quote [#]" will return numbered quote

**!tweet**

"!tweet" Will generate a tweet filled with "Hang out with me at twitch.tv/channelname"

"!tweet [message]" Will generate tweet filled with [message].

**!bitrate**

Returns current bitrate if online

**!link**

"!link (add/remove)" Manages link whitelist. 

"!link list" will output list in logs

**!(botname)**

"!(botname)" >> "I am [botName] v[botVersion]! Now with coffee! Do !commands for more."

"!(botname) command [add/remove/list]" manages commands. ~*Fancy*~

"!(botname) user [info/list]" gives user info. Try to only use list in bot's channel.

"!(botname) permit (user)" will permit a user to one link/ban

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

AI will add any messages that are not command/spam to its database. AI builds sentences from database using a Markov chain.

