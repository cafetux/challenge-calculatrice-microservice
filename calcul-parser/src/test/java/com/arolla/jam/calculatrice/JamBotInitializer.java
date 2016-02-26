package com.arolla.jam.calculatrice;

import com.arolla.jam.calculatrice.infra.messaging.impl.CalculatorHandler;
import com.arolla.jam.calculatrice.infra.messaging.impl.ResultatHandler;
import com.arolla.jam.calculatrice.infra.slack.JamBot;
import com.arolla.jam.calculatrice.infra.slack.Token;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by raphael on 26/02/2016.
 */
@Ignore
public class JamBotInitializer {
    @Test
    public void initASlackCalculator() throws IOException, InterruptedException {
        JamBot bot = JamBot.create()
                .listenTo("general")
                .withToken(Token.BOT1)
                .transformWith(String::intern)
                .build();
        Calculator calculator = new Calculator(bot, bot, new CalculParser());
        bot.addEventHandler(new CalculatorHandler(calculator));
        bot.addEventHandler(new ResultatHandler(calculator));
        bot.start();
    }

}
