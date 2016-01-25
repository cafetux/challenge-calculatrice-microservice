package com.arolla.jam.calculatrice.infra.messaging;

/**
 * Created by cafetux on 23/01/2016.
 */
public interface EventBus {

    void send(String id, EventType calcul, String content);

    void addEventHandler(EventHandler handler);
}
