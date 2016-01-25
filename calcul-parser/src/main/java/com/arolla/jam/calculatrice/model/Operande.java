package com.arolla.jam.calculatrice.model;

import java.io.Serializable;

public abstract class Operande<T> implements Serializable {
    private String id;

    public Operande(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract T getValue();
}