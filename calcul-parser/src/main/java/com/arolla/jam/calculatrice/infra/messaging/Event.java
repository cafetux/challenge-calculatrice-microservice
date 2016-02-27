package com.arolla.jam.calculatrice.infra.messaging;

import java.io.Serializable;

/**
 * Created by cafetux on 23/01/2016.
 */
public class Event extends Message implements Serializable {

    private final EventType type;
    private String id;


    public Event(String from, String identifier,EventType type,String message) {
        super(from,message);
        this.id=identifier;
        this.type=type;
    }

    @Override
    public String getContent() {
        return this.type.name()+"{id:"+id+",content:"+super.getContent()+"}";
    }
}
