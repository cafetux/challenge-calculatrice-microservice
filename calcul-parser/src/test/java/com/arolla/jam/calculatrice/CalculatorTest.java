package com.arolla.jam.calculatrice;

import com.arolla.jam.calculatrice.infra.messaging.EventBus;
import com.arolla.jam.calculatrice.infra.messaging.EventType;
import com.arolla.jam.calculatrice.model.Calcul;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.arolla.jam.calculatrice.model.Operateur.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CalculatorTest {


    private Calculator calculator;

    @Mock
    EventBus eventBus;
    @Mock CalculParser parser;
    private String actualRootCalculId;
    private Calcul expectedRootCalcul;

    @Test
    public void should_return_id_of_calcul(){
        given_calculator();
        that_can_calcul("3+3", new Calcul(ADDITION, 3, 3));

        when_we_ask_for("3+3");

        then_we_retrieve_root_calcul_identifier();
    }

    @Test
    public void should_send_event_for_root_calcul(){
        given_calculator();
        that_can_calcul("3+3", new Calcul(ADDITION, 3, 3));

        when_we_ask_for("3+3");

        then_we_send_event_for_root_calcul();

    }

//    @Test
//    public void should_send_two_event_when_two_operation_on_calcul(){
//        given_calculator();
//        that_can_calcul("3+3-2", new Calcul(ADDITION, 3, new Calcul(SOUSTRACTION,3,2)));
//
//        when_we_ask_for("3+3-2");
//
//        then_we_send_event(expectedRootCalcul.getId(),"["+expectedRootCalcul.getId()+"][3 + ["+expectedRootCalcul.getB().getId()+"]]");
//        then_we_send_event(expectedRootCalcul.getB().getId(), "[" + expectedRootCalcul.getB().getId() + "][3 - 2]");
//
//    }
//
//    @Test
//    public void should_send_three_event_when_three_operation_on_calcul(){
//        given_calculator();
//        that_can_calcul("3+3-2+2", new Calcul(ADDITION, 3, new Calcul(SOUSTRACTION,3,new Calcul(ADDITION,2,2))));
//
//        when_we_ask_for("3+3-2+2");
//
//        then_we_send_event(expectedRootCalcul.getId(),"["+expectedRootCalcul.getId()+"][3 + ["+expectedRootCalcul.getB().getId()+"]]");
//        then_we_send_event(expectedRootCalcul.getB().getId(),"["+expectedRootCalcul.getB().getId()+"][3 - ["+((Calcul)expectedRootCalcul.getB()).getB().getId()+"]]");
//        then_we_send_event(((Calcul)expectedRootCalcul.getB()).getB().getId(),"["+((Calcul)expectedRootCalcul.getB()).getB().getId()+"][2 + 2]");
//
//    }
//
//    @Test
//    public void should_send_3_event_when_3_operation_on_calcul_on_two_sides_branches(){
//        given_calculator();
//        that_can_calcul("3+3*2+2", new Calcul(MULTIPLICATION, new Calcul(ADDITION,3,3), new Calcul(ADDITION,2,2)));
//
//        when_we_ask_for("3+3*2+2");
//
//        then_we_send_event(expectedRootCalcul.getId(),"["+expectedRootCalcul.getId()+"][["+expectedRootCalcul.getA().getId()+"] * ["+expectedRootCalcul.getB().getId()+"]]");
//        then_we_send_event(expectedRootCalcul.getA().getId(),"["+expectedRootCalcul.getA().getId()+"][3 + 3]");
//        then_we_send_event(expectedRootCalcul.getB().getId(),"["+expectedRootCalcul.getB().getId()+"][2 + 2]");
//
//    }

    private void then_we_send_event(String id,String message) {
        verify(eventBus, times(1)).send(id, EventType.CALCUL,message);
    }

    private void then_we_send_event_for_root_calcul() {
        verify(eventBus, times(1)).send(expectedRootCalcul.getId(), EventType.CALCUL,"["+expectedRootCalcul.getId()+"][3 + 3]");
    }

    private void then_we_retrieve_root_calcul_identifier() {
        assertThat(actualRootCalculId).isNotNull().isNotEmpty().isEqualTo(expectedRootCalcul.getId());
    }

    private void that_can_calcul(String calculText, Calcul mockedResult) {
        //when(parser.parse(calculText)).thenReturn(mockedResult);
        expectedRootCalcul = mockedResult;
    }

    private void given_calculator() {
        calculator = new Calculator(eventBus,parser);
    }

    private void when_we_ask_for(String calculExpression) {
        actualRootCalculId = calculator.calcul(calculExpression);
    }

}