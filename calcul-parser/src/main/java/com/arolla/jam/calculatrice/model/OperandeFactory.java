package com.arolla.jam.calculatrice.model;

import java.util.UUID;

/**
 * Created by raphael on 26/02/2016.
 */
public abstract class OperandeFactory {
    public Operande operande(Integer value ) {
        return new IntegerOperande(value);
    }

    public Operande operande(String value) {
        return new FutureOperande(value);
    }

}
