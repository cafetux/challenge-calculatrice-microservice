package com.arolla.jam.calculatrice.calculator.slack;

import com.arolla.jam.calculatrice.calculator.Calculator;
import com.arolla.jam.calculatrice.calculator.calcul.Addition;

public class MainSlack {

    public static final String INPUT_CHANNEL = "general";

    enum SlackToken implements Token {
        //    BOT1("xoxb-19108991843-8XmlhYZSQxvOkbOUnmkkhFoi"),
//    BOT2("xoxb-19249860497-Pw2hdd7nPfkPTlRCaVrlqlW3"),
        YVAN_BOT("xoxb-23381683426-LYfKth8sQXdQrUKy2boD6qcr"),
        CALCULATOR_BOT("xoxb-23394403685-htQGKmvtzYKheURJgHEJsDdU");

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
            Calculator additionCalculator = new Calculator(new Addition());
            JamBot.create()
                    .listenTo(INPUT_CHANNEL)
                    .withToken(SlackToken.CALCULATOR_BOT)
                    .transformWith(additionCalculator::calculate)
                    .build()
                    .start();

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
