package com.arolla.jam.calculatrice.model;

/**
 * Created by cafetux on 25/01/2016.
 */
public class FutureOperande extends Operande<String>{

    public FutureOperande(String identifier) {
        super(identifier);
    }

    @Override
    public String valeur() {
        return identifier();
    }

    @Override
    public String toString() {
        return identifier();
    }
}
