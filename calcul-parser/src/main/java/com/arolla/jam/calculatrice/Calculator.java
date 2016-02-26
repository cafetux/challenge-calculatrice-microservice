package com.arolla.jam.calculatrice;

import com.arolla.jam.calculatrice.infra.messaging.EventBus;
import com.arolla.jam.calculatrice.infra.messaging.UserInterface;
import com.arolla.jam.calculatrice.model.Calcul;

import java.util.ArrayList;
import java.util.List;

import static com.arolla.jam.calculatrice.infra.messaging.EventType.CALCUL;

/**
 * Created by cafetux on 23/01/2016.
 */
public class Calculator {

    private EventBus eventBus;
    private UserInterface userInterface;
    private CalculParser parser;
    private List<String> waitingRootEvents = new ArrayList<>();

    public Calculator(EventBus eventBus, UserInterface userInterface, CalculParser parser) {
        this.eventBus = eventBus;
        this.userInterface = userInterface;
        this.parser = parser;
    }


    public void calcul(String calculExpression) {
        List<Calcul> operations = parser.parse(calculExpression);
        keepRootOperationId(operations);
        operations.forEach(this::sendCalculMessage);
    }

    private void keepRootOperationId(List<Calcul> operations) {
        waitingRootEvents.add(operations.get(operations.size() - 1).getId());
    }

    private void sendCalculMessage(Calcul calcul) {
        eventBus.send(calcul.getId(), CALCUL, CALCUL.toMessage(calcul));
    }

    public void receiveResult(String id, int result) {
        if (waitingRootEvents.contains(id)) {
            waitingRootEvents.remove(id);
            userInterface.say(String.valueOf(result));
        }
    }
}
