package com.arolla.jam.calculatrice.infra;

import com.arolla.jam.calculatrice.CalculParser;
import com.arolla.jam.calculatrice.Calculator;
import com.arolla.jam.calculatrice.infra.irc.CalculatorBot;
import com.arolla.jam.calculatrice.infra.messaging.impl.CalculatorHandler;
import com.arolla.jam.calculatrice.infra.messaging.impl.ResultatHandler;

/**
 * Created by cafetux on 22/01/2016.
 */
public class Main {

    public static final String HOST = "irc.freenode.net";
    public static final String INPUT_CHANNEL = "#jam_input";
    public static final String EVENTS_CHANNEL = "#jam_events";

    public static void main(String [] args) {
        try {
            CalculatorBot bot = new CalculatorBot(HOST, EVENTS_CHANNEL,INPUT_CHANNEL, "Calculator");
            bot.joinChannel(INPUT_CHANNEL);
            bot.joinChannel(EVENTS_CHANNEL);

            Calculator calculator = new Calculator(bot,bot,new CalculParser());
            bot.addEventHandler(new CalculatorHandler(calculator));
            bot.addEventHandler(new ResultatHandler(calculator));

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
