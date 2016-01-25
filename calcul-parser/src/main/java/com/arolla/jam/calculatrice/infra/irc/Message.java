package com.arolla.jam.calculatrice.infra.irc;

public class Message {

    private String from;
    private String content;
    
    public Message(String from, String content) {
        super();
        this.from = from;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
