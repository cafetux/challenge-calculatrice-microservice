package com.arolla.jam.calculatrice.calculator;

/**
 * Created by MicroOnde on 27/02/2016.
 */
public interface Calcul {

    boolean accept(String pattern);

    String compute(int op1, int op2);

}
