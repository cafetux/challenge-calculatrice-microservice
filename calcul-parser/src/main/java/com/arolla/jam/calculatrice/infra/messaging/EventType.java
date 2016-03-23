package com.arolla.jam.calculatrice.infra.messaging;

import com.arolla.jam.calculatrice.model.operation.Calcul;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public enum EventType {
        RESULTAT{
            @Override
            public String toMessage(Calcul calcul) {
                throw new NotImplementedException();
            }
        },
        CALCUL {
            @Override
            public String toMessage(Calcul calcul) {
                return String.format("|%s|%s|%s;%s|",calcul.getOperateur(),calcul.getId(), calcul.getA().valeur(), calcul.getB().valeur());
            }

        };


    public abstract String toMessage(Calcul calcul);
    }
