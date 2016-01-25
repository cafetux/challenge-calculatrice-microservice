package com.arolla.jam.calculatrice.model;

/**
 * Created by cafetux on 25/01/2016.
 */
public class IntegerOperande extends Operande<Integer>{

    private Integer value;

    public IntegerOperande(Integer value) {
        super("INTEGER:"+value);
        this.value=value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerOperande)) return false;

        IntegerOperande that = (IntegerOperande) o;

        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
