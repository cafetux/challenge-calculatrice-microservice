package com.arolla.jam.calculatrice;

import com.arolla.jam.calculatrice.infra.messaging.EventBus;
import com.arolla.jam.calculatrice.model.Calcul;

import static com.arolla.jam.calculatrice.infra.messaging.EventType.CALCUL;

/**
 * Created by cafetux on 23/01/2016.
 */
public class Calculator {

    private EventBus eventBus;
    private CalculParser parser;

    public Calculator(EventBus eventBus,CalculParser parser) {
        this.eventBus = eventBus;
        this.parser=parser;
    }


    public String calcul(String calculExpression){
        parser.parse(calculExpression).forEach(this::sendCalculMessage);
        return "haha";
    }

    private void sendCalculMessage(Calcul calcul) {
        eventBus.send(calcul.getId(), CALCUL,CALCUL.toMessage(calcul));
    }


}
