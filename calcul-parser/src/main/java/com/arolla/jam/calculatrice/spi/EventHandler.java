package com.arolla.jam.calculatrice.spi;

import com.arolla.jam.calculatrice.infra.messaging.Message;

/**
 * Created by cafetux on 23/01/2016.
 */
public interface EventHandler {

    boolean accept(Message message);

    void handle(Message message);

}
