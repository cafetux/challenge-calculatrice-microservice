package com.arolla.jam.calculatrice.infra.slack;

import com.arolla.jam.calculatrice.infra.messaging.*;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by raphael_squelbut on 22/02/16.
 */
public abstract class JamBot implements EventBus, UserInterface {
    private static final Logger LOGGER = LoggerFactory.getLogger(JamBot.class);

    private final SlackSession session;

    private List<EventHandler> handlers = new ArrayList<>();

    public abstract boolean itIsNotForMe(final String message);

    public abstract String readingChannel();

    public abstract String writingChannel();

    public abstract String produceResponse(String message);

    public static JamBotBuilder create() {
        return new JamBotBuilder();
    }

    public JamBot(SlackSession session) {
        this.session = session;
    }

    @Override
    public void say(String content) {
        final SlackChannel writingChannel = session.findChannelByName("general");
        session.sendMessage( //
                writingChannel, //
                content, //
                null);

    }

    @Override
    public void send(String id, EventType calcul, String content) {
        final SlackChannel writingChannel = session.findChannelByName("general");
        session.sendMessage( //
                writingChannel, //
                String.format("[%s]%s", calcul, content), //
                null);
    }

    @Override
    public void addEventHandler(EventHandler handler) {
        handlers.add(handler);
    }

    public void start() throws IOException, InterruptedException {
        session.addMessagePostedListener(createMessagePostedListener());
        session.connect();

        while (true) {
            Thread.sleep(100);
        }
    }

    private SlackMessagePostedListener createMessagePostedListener() {
        return (event, session) -> {
            if (shouldIForgetIt(event, session)) {
                return;
            }

            String response = produceResponse(event.getMessageContent());
            final Message message = new Message(event.getChannel().getName(), response);
            for (EventHandler handler : handlers) {
                if (handler.accept(message)) {
                    handler.handle(message);
                }
            }
        };
    }

    private SlackChannel findResponseChannel(final SlackMessagePosted event,
                                             final SlackSession session) {
        return writingChannel() != null ?
                session.findChannelByName(writingChannel()) :
                event.getChannel();
    }

    private boolean shouldIForgetIt(final SlackMessagePosted event,
                                    final SlackSession session) {
        boolean itSMeWhoSendMessage = itSMeWhoSentIt(event, session);
        final boolean shouldIForgetIt =
                itSMeWhoSendMessage || itIsNotForMe(event.getMessageContent());
        LOGGER.debug("shouldIForgetIt : " + shouldIForgetIt);
        return shouldIForgetIt;
    }

    private boolean itSMeWhoSentIt(final SlackMessagePosted event,
                                   final SlackSession session) {
        return event.getSender()
                .getId()
                .equals(session.sessionPersona().getId());
    }

}