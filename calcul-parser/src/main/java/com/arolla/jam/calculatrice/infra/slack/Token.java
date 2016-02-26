package com.arolla.jam.calculatrice.infra.slack;

/**
 * Created by raphael on 22/02/2016.
 */
public enum Token {
    BOT1("xoxb-19108991843-8XmlhYZSQxvOkbOUnmkkhFoi"),
    BOT2("xoxb-19249860497-Pw2hdd7nPfkPTlRCaVrlqlW3");


    private final String token;

     Token(String token) {
        this.token = token;
    }

    public String get() {
        return token;
    }
}
