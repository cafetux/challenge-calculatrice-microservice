package com.arolla.jam.calculatrice.calculator.slack;

import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackMessageHandle;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.events.SlackMessagePosted;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.ullink.slack.simpleslackapi.listeners.SlackMessagePostedListener;
import com.ullink.slack.simpleslackapi.replies.SlackChannelReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by raphael_squelbut on 22/02/16.
 */
public abstract class JamBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(JamBot.class);

    public abstract boolean itIsNotForMe(final String message);

    public abstract String getToken();

    public abstract String readingChannel();

    public abstract String writingChannel();

    public abstract String produceResponse(String message);

    public static JamBotBuilder create() {
        return new JamBotBuilder();
    }

    protected SlackMessagePostedListener createMessagePostedListener() {
        return (event, session) -> {
//            if (shouldIForgetIt(event, session)) {
//                return;
//            }

            String response = produceResponse(event.getMessageContent());

            //let's send a message
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final SlackChannel responseChannel = findResponseChannel(event,
                        session);
                session.sendMessage( //
                        responseChannel, //
                        response, //
                        null);
            }).start();
        };
    }

    protected SlackChannel findResponseChannel(final SlackMessagePosted event,
                                               final SlackSession session) {
        return writingChannel() != null ?
                session.findChannelByName(readingChannel()) :
                event.getChannel();
    }

    protected boolean shouldIForgetIt(final SlackMessagePosted event,
                                      final SlackSession session) {
        boolean itSMeWhoSendMessage = itSMeWhoSentMessage(event, session);
        final boolean shouldIForgetIt =
                itSMeWhoSendMessage || itIsNotForMe(event.getMessageContent());
        LOGGER.debug("shouldIForgetIt : " + shouldIForgetIt);
        return shouldIForgetIt;
    }

    protected boolean itSMeWhoSentMessage(final SlackMessagePosted event,
                                          final SlackSession session) {
        return event.getSender()
                .getId()
                .equals(session.sessionPersona().getId());
    }

    public void start() throws IOException, InterruptedException {
        final SlackSession session = SlackSessionFactory
                .createWebSocketSlackSession(getToken());

        session.addMessagePostedListener(createMessagePostedListener());
        session.connect();
        final String channelToJoin =
                readingChannel() != null ? readingChannel() : "random";
        final SlackMessageHandle<SlackChannelReply> channelJoinedReply =
                session
                        .joinChannel(channelToJoin);
        LOGGER.debug("Can join " + channelToJoin + " : " + channelJoinedReply);
        while (true) {
            Thread.sleep(1000);
        }
    }
}