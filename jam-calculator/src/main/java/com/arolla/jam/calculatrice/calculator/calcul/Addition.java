package com.arolla.jam.calculatrice.calculator.calcul;

import com.arolla.jam.calculatrice.calculator.Calcul;

/**
 * Created by MicroOnde on 27/02/2016.
 */
public class Addition implements Calcul {

    public static final String ADDITION = "ADDITION";

    @Override
    public boolean accept(String pattern) {
        return ADDITION.equals(pattern);
    }

    @Override
    public String compute(int op1, int op2) {
        return String.valueOf(op1 + op2);
    }
}
