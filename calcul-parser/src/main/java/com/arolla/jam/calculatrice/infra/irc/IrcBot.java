package com.arolla.jam.calculatrice.infra.irc;

import com.arolla.jam.calculatrice.infra.messaging.Message;
import org.jibble.pircbot.PircBot;

import java.util.stream.Stream;

public abstract class IrcBot extends PircBot {

    private String host;

    public IrcBot(String host,String name) {
        super();
        this.host = host;
        this.setName(name);
        this.setVerbose(true);
        try {
            this.connect(host);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean isConnected(String channel) {
        return this.isConnected() 
            && Stream.of(this.getChannels()).filter(channel::equals).findFirst().isPresent();
    }

    public void say(String channel,String msg) {
        sendMessage(channel, msg);
    }

    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        Message msg = new Message(sender, message);
        if (filter(msg)) {
            consume(msg);
        }
    }
    @Override
    public void onPrivateMessage(String sender, String login, String hostname, String message) {
        onMessage("PRIVATE",sender,login,hostname,message);
    }

    protected abstract boolean filter(Message msg);
    protected abstract void consume(Message msg);

}
