package com.arolla.jam.calculatrice.infra.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by cafetux on 29/03/2016.
 */
public class Calcul{

    private String calcul;
    private String resultat;

    public Calcul() {
    }

    public Calcul(String calcul, String resultat) {
        this.calcul = calcul;
        this.resultat = resultat;
    }

    @JsonProperty
    public String getCalcul() {
        return calcul;
    }


    @JsonProperty
    public String getResultat() {
        return resultat;
    }


}
