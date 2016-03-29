package com.arolla.jam.calculatrice.infra.messaging.impl;

import com.arolla.jam.calculatrice.Calculator;
import com.arolla.jam.calculatrice.infra.messaging.Message;
import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ResultatHandlerTest {

    @InjectMocks
    ResultatHandler handler;

    @Mock
    private Calculator calculator;
    private Condition<Message> ACCEPTED;
    @Before
    public void init(){
        ACCEPTED = new Condition<>(handler::accept, "accepted by result handler");
    }

    @Test
    public void should_accept_result_messages(){
        assertThat(message("|RESULTAT|" + uuid() + "|45|")).is(ACCEPTED);
    }

    @Test
    public void should_accept_result_messages_even_if_space_on_result(){
        assertThat(message("|RESULTAT|" + uuid() + "| 45|")).is(ACCEPTED);
    }

    @Test
    public void should_accept_result_messages_even_if_space_before_uuid(){
        assertThat(message("|RESULTAT| " + uuid() + "|45|")).is(ACCEPTED);
    }

    @Test
    public void should_accept_result_messages_even_if_space_on_result_tag(){
        assertThat(message("|RESULTAT |" + uuid() + "|45|")).is(ACCEPTED);
    }

    @Test
    public void should_not_accept_word_results(){
        assertThat(message("|RESULTAT|" + uuid() + "|douze|")).isNot(ACCEPTED);
    }

    @Test
    public void should_treat_message(){
        Message message = message("|RESULTAT|8c466e118f7944d6877246e5518120d6|34|");
        handler.handle(message);
        Mockito.verify(calculator).receiveResult("8c466e118f7944d6877246e5518120d6",34);
    }

    @Test
    public void should_treat_message_even_have_space(){


        handler.handle( message("|RESULTAT|8c466e118f7944d6877246e5518120d6| 34|"));
        handler.handle( message("| RESULTAT|8c466e118f7944d6877246e5518120d6|34|"));
        handler.handle( message("|RESULTAT|8c466e118f7944d6877246e5518120d6 | 34|"));

        Mockito.verify(calculator,times(3)).receiveResult("8c466e118f7944d6877246e5518120d6",34);
    }

    private Message message(String content) {
        return new Message("titi", content);
    }

    private String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}