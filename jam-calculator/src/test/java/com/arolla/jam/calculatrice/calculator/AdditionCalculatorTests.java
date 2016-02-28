package com.arolla.jam.calculatrice.calculator;

import com.arolla.jam.calculatrice.calculator.calcul.Addition;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AdditionCalculatorTests {

    private Calculator calculator;

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator(new Addition());
    }

    @Test
    public void must_contains_4_fields() throws Exception {
        assertThat(calculator.calculate("QSDFQSDF")).isEqualTo("");
    }

    @Test
    public void first_field_must_be_CALCUL() throws Exception {
        assertThat(calculator.calculate("[CALCULL][ADDITION][542bc5d232d84f45a66f9fbb973547da][1;1]")).isEqualTo("");
    }

    @Test
    public void second_field_must_be_ADDITION() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITIONN][542bc5d232d84f45a66f9fbb973547da][1;1]")).isEqualTo("");
    }

    @Test
    public void fourth_field_must_be_2_int_separated_by_semi_comma() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][542bc5d232d84f45a66f9fbb973547da][1-1]")).isEqualTo("");
    }

    @Test
    public void valid_calculation_result_must_contains_RESULTAT() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][542bc5d232d84f45a66f9fbb973547da][1;1]")).startsWith("[RESULTAT]");
    }

    @Test
    public void valid_calculation_result_must_contains_commandUUID() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][REQUIESTuuid][1;1]")).startsWith("[RESULTAT][REQUIESTuuid]");
    }

    @Test
    public void calculateSimpleAddition() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][542bc5d232d84f45a66f9fbb973547da][1;1]")).isEqualTo("[RESULTAT][542bc5d232d84f45a66f9fbb973547da][2]");
    }

    @Test
    public void calculate_4_plus_4_returns_8() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][mailol][4;4]")).isEqualTo("[RESULTAT][mailol][8]");
    }

    @Test
    public void shoud_wait_for_result_if_calcul_between_uuid_and_operand() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][shouldwait][uuidTOwait;1]")).isEqualTo("");
        assertThat(calculator.getResultsCache()).containsKey("uuidTOwait");
        assertThat(calculator.calculate("[RESULTAT][uuidTOwait][41]")).isEqualTo("[RESULTAT][shouldwait][42]");
    }

    @Test
    public void shoud_wait_for_result_if_calcul_between_and_operand_uuid() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][shouldwait][1;uuidTOwait]")).isEqualTo("");
        assertThat(calculator.getResultsCache()).containsKey("uuidTOwait");
        assertThat(calculator.calculate("[RESULTAT][uuidTOwait][41]")).isEqualTo("[RESULTAT][shouldwait][42]");
    }

    @Test
    public void shoud_also_wait_for_result_if_calcul_between_uuid_and_uuid() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][shouldwait][uuidTOwait;another1]")).isEqualTo("");
        assertThat(calculator.getResultsCache()).containsKey("uuidTOwait");
        assertThat(calculator.calculate("[RESULTAT][uuidTOwait][50]")).isEqualTo("");
        assertThat(calculator.calculate("[RESULTAT][another1][-8]")).isEqualTo("[RESULTAT][shouldwait][42]");
    }

    @Test
    public void calculation_received_after_result() throws Exception {
        assertThat(calculator.calculate("[CALCUL][ADDITION][32c8928739e04a0a95d9af342b8256eb][1;1]")).isEqualTo("[RESULTAT][32c8928739e04a0a95d9af342b8256eb][2]");
        assertThat(calculator.calculate("[CALCUL][ADDITION][3e59557f005a48e3bcafe51d8e6882de][32c8928739e04a0a95d9af342b8256eb;1]")).isEqualTo("[RESULTAT][3e59557f005a48e3bcafe51d8e6882de][3]");
    }

}
