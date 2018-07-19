package com.arolla.jam.calculatrice;

import com.arolla.jam.calculatrice.model.operation.Calcul;
import com.arolla.jam.calculatrice.model.operation.IntegerOperande;
import com.arolla.jam.calculatrice.model.operation.Operande;
import com.arolla.jam.calculatrice.model.parsing.CalculParser;
import org.junit.Test;

import java.util.List;
import java.util.function.Predicate;

import static com.arolla.jam.calculatrice.model.operation.Operateur.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author fabien_maury
 */
public class CalculStringParserTest {

    public static final Predicate<String> IS_UUID = x -> x.matches("[a-z0-9]{32}");
    private CalculParser parser = new CalculParser();

	@Test
	public void should_split_plus_operation(){
		String calculText="3+5";
		List<Calcul> calculs=parser.parse(calculText);
        assertThat(calculs).hasSize(1);
		assertThat(calculs.get(0).getOperateur()).isEqualTo(ADDITION);
		assertThat(calculs.get(0).getA().valeur()).isEqualTo(3);
		assertThat(calculs.get(0).getB().valeur()).isEqualTo(5);

	}

	@Test
	public void should_split_moins_operation(){
		List<Calcul> calculs=parser.parse("4-2");
        assertThat(calculs).hasSize(1);
		assertThat(calculs.get(0).getOperateur()).isEqualTo(SOUSTRACTION);
		assertThat(calculs.get(0).getA().valeur()).isEqualTo(4);
		assertThat(calculs.get(0).getB().valeur()).isEqualTo(2);

	}

	@Test
	public void should_split_multiplication_operation(){

		List<Calcul> calculs = parser.parse("6*12");
        assertThat(calculs).hasSize(1);
		assertThat(calculs.get(0).getOperateur()).isEqualTo(MULTIPLICATION);
		assertThat(calculs.get(0).getA().valeur()).isEqualTo(6);
		assertThat(calculs.get(0).getB().valeur()).isEqualTo(12);
	}

	@Test
	public void should_split_multiplication_operation_with_second_opearande_negative(){

		List<Calcul> calcul1 = parser.parse("6*-12");
		assertThat(calcul1.get(0).getOperateur()).isEqualTo(MULTIPLICATION);
		assertThat(calcul1.get(0).getA().valeur()).isEqualTo(6);
		assertThat(calcul1.get(0).getB().valeur()).isEqualTo(-12);
	}

	@Test
	public void should_split_multiplication_operation_on_negatives_operandes(){

		List<Calcul> calcul1 = parser.parse("-6*-12");
		assertThat(calcul1.get(0).getOperateur()).isEqualTo(MULTIPLICATION);
		assertThat(calcul1.get(0).getA().valeur()).isEqualTo(-6);
		assertThat(calcul1.get(0).getB().valeur()).isEqualTo(-12);
	}

	@Test
	public void should_split_multiplication_operation_with_first_opearande_negative(){

		List<Calcul> calcul1 = parser.parse("-6*12");
		assertThat(calcul1.get(0).getOperateur()).isEqualTo(MULTIPLICATION);
		assertThat(calcul1.get(0).getA().valeur()).isEqualTo(-6);
		assertThat(calcul1.get(0).getB().valeur()).isEqualTo(12);
	}

    @Test(expected = IllegalArgumentException.class)
    public void should_reject_input_when_uknow_operation_sign_used(){
        parser.parse("-6@12");
    }

	@Test
	public void should_split_division_operation(){
		List<Calcul> calcul=parser.parse("12/3");
		assertThat(calcul.get(0).getOperateur()).isEqualTo(DIVISION);
		assertThat(calcul.get(0).getA()).isEqualTo(operande(12));
		assertThat(calcul.get(0).getB()).isEqualTo(operande(3));

	}

	@Test
	public void should_split_soustraction_operation_on_negatives_operandes() {

		List<Calcul> calcul1 = parser.parse("-6-12");
		assertThat(calcul1.get(0).getOperateur()).isEqualTo(SOUSTRACTION);
		assertThat(calcul1.get(0).getA().valeur()).isEqualTo(-6);
		assertThat(calcul1.get(0).getB().valeur()).isEqualTo(12);
	}

	private static IntegerOperande operande(final int value) {
		return new IntegerOperande(value);
	}

    @Test
    public void should_split_multiple_operation(){

        List<Calcul> calcul=parser.parse("3+5-2");
        assertThat(calcul.get(0).getOperateur()).isEqualTo(ADDITION);
        assertThat(calcul.get(0).getA()).isEqualTo(operande(3));
        assertThat(calcul.get(0).getB()).isEqualTo(operande(5));

        Calcul calculLazy = calcul.get(1);
        assertThat(calculLazy.getOperateur()).isEqualTo(SOUSTRACTION);
        assertThat(calculLazy.getA().identifier()).matches(IS_UUID);
        assertThat(calculLazy.getB()).isEqualTo(operande(2));
    }

    @Test
    public void should_split_multiple_operation_finish_by_division(){

        List<Calcul> calcul=parser.parse("3+5/2");
        assertThat(calcul.get(0).getOperateur()).isEqualTo(DIVISION);
        assertThat(calcul.get(0).getA()).isEqualTo(operande(5));
        assertThat(calcul.get(0).getB()).isEqualTo(operande(2));

        Calcul calculLazy = calcul.get(1);
        assertThat(calculLazy.getOperateur()).isEqualTo(ADDITION);
        assertThat(calculLazy.getA()).isEqualTo(operande(3));
        assertThat(calculLazy.getB().identifier()).matches(IS_UUID);
    }

    @Test
    public void should_split_multiple_operation_with_division(){

        List<Calcul> calcul=parser.parse("3+5/2-5");
        assertThat(calcul.get(0).getOperateur()).isEqualTo(DIVISION);
        assertThat(calcul.get(0).getA()).isEqualTo(operande(5));
        assertThat(calcul.get(0).getB()).isEqualTo(operande(2));

        Calcul calculLazy = calcul.get(1);
        assertThat(calculLazy.getOperateur()).isEqualTo(ADDITION);
        assertThat(calculLazy.getA()).isEqualTo(operande(3));
        assertThat(calculLazy.getB().identifier()).matches(IS_UUID);

        Calcul calculLazy2 = calcul.get(2);
        assertThat(calculLazy2.getOperateur()).isEqualTo(SOUSTRACTION);
        assertThat(calculLazy2.getA().identifier()).matches(IS_UUID);
        assertThat(calculLazy2.getB()).isEqualTo(operande(5));
    }
    @Test
    public void should_split_multiple_operation_with_multiplication(){

        List<Calcul> calcul=parser.parse("3-5*2+5");
        assertThat(calcul.get(0).getOperateur()).isEqualTo(MULTIPLICATION);
        assertThat(calcul.get(0).getA()).isEqualTo(operande(5));
        assertThat(calcul.get(0).getB()).isEqualTo(operande(2));

        Calcul calculLazy = calcul.get(1);
        assertThat(calculLazy.getOperateur()).isEqualTo(ADDITION);
        assertThat(calculLazy.getA().identifier()).matches(IS_UUID);
        assertThat(calculLazy.getB()).isEqualTo(operande(5));

        Calcul calculLazy2 = calcul.get(2);
        assertThat(calculLazy2.getOperateur()).isEqualTo(SOUSTRACTION);
        assertThat(calculLazy2.getA()).isEqualTo(operande(3));
        assertThat(calculLazy2.getB().identifier()).matches(IS_UUID);
    }

    @Test
    public void should_priorise_multiplication_and_division() {

        //Calcul calcul = parser.parse("3+5*2+3-5/2-8+4*2");

    }

    @Test
    public void should_priorise_multiplication(){

        List<Calcul> calcul=parser.parse("3+5*2");
        assertThat(calcul.get(0).getOperateur()).isEqualTo(MULTIPLICATION);
        assertThat(calcul.get(0).getA()).isEqualTo(operande(5));
        assertThat(calcul.get(0).getB()).isEqualTo(operande(2));

        Calcul calculLazy = calcul.get(1);
        assertThat(calculLazy.getOperateur()).isEqualTo(ADDITION);
        assertThat(calculLazy.getA()).isEqualTo(operande(3));
        assertThat(calculLazy.getB().identifier()).matches(IS_UUID);
    }

	@Test
	public void should_two_direct_numbers_with_same_value_be_equals(){
		Operande a = operande(3);
		Operande b = operande(3);
		assertThat(a).isEqualTo(b);
	}

	@Test
	public void should_two_direct_numbers_with_differents_value_not_be_equals(){
		Operande a = operande(3);
		Operande b = operande(5);
		assertThat(a).isNotEqualTo(b);
	}





}

