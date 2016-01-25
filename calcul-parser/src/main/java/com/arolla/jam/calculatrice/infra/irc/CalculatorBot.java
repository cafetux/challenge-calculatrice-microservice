package com.arolla.jam.calculatrice.infra.irc;

import com.arolla.jam.calculatrice.infra.messaging.EventBus;
import com.arolla.jam.calculatrice.infra.messaging.EventHandler;
import com.arolla.jam.calculatrice.infra.messaging.EventType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cafetux on 22/01/2016.
 */
public class CalculatorBot extends IrcBot implements EventBus{

    private final String eventChannel;
    private List<EventHandler> handlers = new ArrayList<>();

    public CalculatorBot(String host, String channel, String name) {
        super(host, name);
        this.eventChannel=channel;
    }

    @Override
    protected boolean filter(Message msg) {
        return true;
    }

    @Override
    protected void consume(Message msg) {
        for (EventHandler handler : handlers) {
            if(handler.accept(msg)){
                handler.handle(msg);
            }
        }
    }


    @Override
    public void send(String id, EventType calcul, String content) {
        say(eventChannel,String.format("[%s]%s",calcul,content));

    }

    @Override
    public void addEventHandler(EventHandler handler) {
        this.handlers.add(handler);
    }
}
