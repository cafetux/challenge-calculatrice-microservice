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
}
