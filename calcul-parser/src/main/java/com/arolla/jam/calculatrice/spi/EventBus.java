package com.arolla.jam.calculatrice.spi;

import com.arolla.jam.calculatrice.infra.messaging.EventType;

/**
 * Created by cafetux on 23/01/2016.
 */
public interface EventBus {

    void send(String id, EventType calcul, String content);

    void addEventHandler(EventHandler handler);
}
