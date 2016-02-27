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
                .withToken(SlackToken.YVAN_BOT)
                .transformWith(String::intern)
                .build();
        Calculator calculator = new Calculator(bot, bot, new CalculParser());
        bot.addEventHandler(new CalculatorHandler(calculator));
        bot.addEventHandler(new ResultatHandler(calculator));
        bot.start();
    }

    enum SlackToken implements Token {
        YVAN_BOT("xoxb-23381683426-LYfKth8sQXdQrUKy2boD6qcr");

        private final String token;

        SlackToken(String token) {
            this.token = token;
        }

        @Override
        public String get() {
            return token;
        }
    }

}
