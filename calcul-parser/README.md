## IRC
client IRC: https://kiwiirc.com/client
2 channels:
jam_input: c'est là que l'utilisateur interragit: il tape des operations et reçoit le résultat
jam_events: c'est ici que les bots broadcasts leurs événements

## Slack

- Go to a room
- Create a bot for that room [HELP](https://api.slack.com/bot-users) - [CREATE BOT LINK](https://my.slack.com/services/new/bot)
- Get the token
- Do the changes in MainSlack
```java
JamBot bot = JamBot.create()
                    .listenTo(**HERE**)
                    .withToken(**HERE**)
                    .transformWith(String::intern)
                    .build();
```

### Syntax

```
me
1+1
calculatorBOT
[CALCUL][ADDITION][5f03b63bfcbc4043af43b2a4bbf955e4][1;1]
```

