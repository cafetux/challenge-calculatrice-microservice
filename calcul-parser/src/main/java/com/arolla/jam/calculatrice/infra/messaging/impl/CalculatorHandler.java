package com.arolla.jam.calculatrice.infra.messaging.impl;

import com.arolla.jam.calculatrice.Calculator;
import com.arolla.jam.calculatrice.infra.irc.Message;
import com.arolla.jam.calculatrice.infra.messaging.EventHandler;

import java.util.regex.Pattern;

/**
 * Created by cafetux on 23/01/2016.
 */
public class CalculatorHandler implements EventHandler{

    public static Pattern CALCUL_PATTERN = Pattern.compile("^-?\\d+([\\+\\-\\*/]-?\\d+)+$");

    private Calculator calculator;

    public CalculatorHandler(Calculator calculator) {
        this.calculator=calculator;
    }

    @Override
    public boolean accept(Message message) {
        boolean accept = CALCUL_PATTERN.matcher(message.getContent()).find();
        if (accept) {
            System.out.println("CalculatorHandler.accept: "+message);
        } else {
            System.out.println("CalculatorHandler.ignore: "+message);
        }
        return accept;
    }

    @Override
    public void handle(Message message) {
        calculator.calcul(message.getContent());
    }
}
