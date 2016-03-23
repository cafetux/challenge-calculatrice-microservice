package com.arolla.jam.calculatrice.infra.messaging.impl;

import com.arolla.jam.calculatrice.Calculator;
import com.arolla.jam.calculatrice.spi.EventHandler;
import com.arolla.jam.calculatrice.infra.messaging.Message;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cafetux on 23/01/2016.
 */
public class ResultatHandler implements EventHandler{

    public static Pattern RESULTAT_PATTERN = Pattern.compile("^\\|RESULTAT\\|([a-zA-Z0-9]{32})\\|([ \\d]+)\\|$");

    private Calculator calculator;

    public ResultatHandler(Calculator calculator) {
        this.calculator=calculator;
    }

    @Override
    public boolean accept(Message message) {

        if (isResultat(trim(message))) {
            System.out.println("ResultatHandler.accept: "+message);
            return true;
        } else {
            System.out.println("ResultatHandler.ignore: "+message);
            return false;
        }
    }

    private String trim(Message message) {
        return message.getContent().replace(" ","");
    }

    private boolean isResultat(String messageContent) {
        return RESULTAT_PATTERN.matcher(messageContent).find();
    }

    @Override
    public void handle(Message message) {
        Matcher matcher = RESULTAT_PATTERN.matcher(trim(message));
        matcher.find();
        calculator.receiveResult(matcher.group(1), Integer.parseInt(matcher.group(2)));
    }
}
