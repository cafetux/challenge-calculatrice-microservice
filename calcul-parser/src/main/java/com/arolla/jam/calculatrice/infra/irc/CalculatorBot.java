package com.arolla.jam.calculatrice.infra.irc;

import com.arolla.jam.calculatrice.infra.messaging.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cafetux on 22/01/2016.
 */
public class CalculatorBot extends IrcBot implements EventBus,UserInterface{

    private final String eventChannel;
    private final String userChannel;
    private List<EventHandler> handlers = new ArrayList<>();

    public CalculatorBot(String host, String eventChannel, String userChannel, String name) {
        super(host, name);
        this.eventChannel=eventChannel;
        this.userChannel=userChannel;
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

    @Override
    public void say(String content) {
        say(userChannel,content);
    }
}
