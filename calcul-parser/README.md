## IRC

un client IRC Web: https://kiwiirc.com/client
server IRC: irc.freenode.net

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
me(chan jam_input)
1+1
calculatorBOT (chan jam_events)
[CALCUL][ADDITION][5f03b63bfcbc4043af43b2a4bbf955e4][1;1]
AdditionBOT (chan jam_events)
[RESULTAT][5f03b63bfcbc4043af43b2a4bbf955e4][2]

calculatorBOT (chan jam_input)
1+1=2
```

Cas d'un calcul "Lazy". Le bot de soustraction attends le résultat de l'operation pour effectuer son calcul
```
me(chan jam_input)
1+1-2

calculatorBOT (chan jam_events)
[CALCUL][ADDITION][5f03b63bfcbc4043af43b2a4bbf955e4][1;1]
[CALCUL][SOUSTRACTION][406bbf43af4acb2a4bbf955e4b65f033][5f03b63bfcbc4043af43b2a4bbf955e4;2]

AdditionBOT (chan jam_events)
[RESULTAT][5f03b63bfcbc4043af43b2a4bbf955e4][2]

soustractionBOT (chan jam_events)
[RESULTAT][406bbf43af4acb2a4bbf955e4b65f033][0]

calculatorBOT (chan jam_input)
1+1-2=0
```
