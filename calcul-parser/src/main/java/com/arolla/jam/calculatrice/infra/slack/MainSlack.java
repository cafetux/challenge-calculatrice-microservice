package com.arolla.jam.calculatrice.infra.slack;

import com.arolla.jam.calculatrice.CalculParser;
import com.arolla.jam.calculatrice.Calculator;
import com.arolla.jam.calculatrice.infra.messaging.impl.CalculatorHandler;
import com.arolla.jam.calculatrice.infra.messaging.impl.ResultatHandler;

/**
 * Created by cafetux on 22/01/2016.
 */
public class MainSlack {

    public static final String INPUT_CHANNEL = "general";
    public static final String EVENTS_CHANNEL = "#jam_events";

    enum SlackToken implements Token {
        //    BOT1("xoxb-19108991843-8XmlhYZSQxvOkbOUnmkkhFoi"),
//    BOT2("xoxb-19249860497-Pw2hdd7nPfkPTlRCaVrlqlW3"),
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

    public static void main(String [] args) {
        try {
            JamBot bot = JamBot.create()
                    .listenTo(INPUT_CHANNEL)
                    .withToken(SlackToken.YVAN_BOT)
                    .transformWith(String::intern)
                    .build();
            Calculator calculator = new Calculator(bot, bot, new CalculParser());
            bot.addEventHandler(new CalculatorHandler(calculator));
            bot.addEventHandler(new ResultatHandler(calculator));
            bot.start();

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
