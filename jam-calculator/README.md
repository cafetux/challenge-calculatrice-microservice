# JAM-CALCULATOR

A calculator client microservice for the Jam

## Slack

- Go to a room
- Create a bot for that room [HELP](https://api.slack.com/bot-users) - [CREATE BOT LINK](https://my.slack.com/services/new/bot)
- Get the token
- Do the changes in MainSlack
```java
JamBot bot = JamBot.create()
                    .listenTo(**HERE**)
                    .withToken(**HERE**)
                    .transformWith(additionCalculator::calculate)
                    .build();
```

### Syntax

```
me [10:58 PM] 
1+2+3+4

calculatorBOT [10:58 PM] 
[CALCUL][ADDITION][cc9a812ba900413aa4bee57cfa9a8998][1;2]

[10:58] 
[CALCUL][ADDITION][02f18131702840d58ebd9cfef3693c90][cc9a812ba900413aa4bee57cfa9a8998;3]

[10:58] 
[CALCUL][ADDITION][ca3d3d4fb3664fc586ffc387ab257499][02f18131702840d58ebd9cfef3693c90;4]

additionerBOT [10:58 PM] 
[RESULTAT][cc9a812ba900413aa4bee57cfa9a8998][3]

[10:58] 
[RESULTAT][02f18131702840d58ebd9cfef3693c90][6]

[10:58] 
[RESULTAT][ca3d3d4fb3664fc586ffc387ab257499][10]

calculatorBOT [10:58 PM] 
10
```