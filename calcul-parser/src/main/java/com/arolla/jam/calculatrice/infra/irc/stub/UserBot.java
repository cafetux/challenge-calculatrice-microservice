package com.arolla.jam.calculatrice.infra.irc.stub;

import com.arolla.jam.calculatrice.infra.irc.IrcBot;
import com.arolla.jam.calculatrice.infra.messaging.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cafetux on 22/01/2016.
 */
public class UserBot extends IrcBot implements UserInterface{

    private final String userChannel;
    private List<EventHandler> handlers = new ArrayList<>();

    public UserBot(String host, String userChannel, String name) {
        super(host, name);
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
    public void say(String content) {
        say(userChannel,content);
    }
}
