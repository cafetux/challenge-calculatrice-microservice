package com.arolla.jam.calculatrice;

import com.arolla.jam.calculatrice.model.parsing.Lexer;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LexerTest {

    private Lexer lexer = new Lexer();

    @Test
    public void should_split_simple_addition(){
        assertThat(lexer.lexicalSplit("3+5")).containsExactly("3", "+", "5");
    }

    @Test
    public void should_split_simple_soustrsction(){
        assertThat(lexer.lexicalSplit("3-5")).containsExactly("3", "-", "5");
    }


    @Test
    public void should_split_simple_multiplications(){
        assertThat(lexer.lexicalSplit("3*5")).containsExactly("3", "*", "5");
    }

    @Test
    public void should_split_simple_division(){
        assertThat(lexer.lexicalSplit("3/5")).containsExactly("3", "/", "5");
    }
    @Test
    public void should_split_negative_number(){
        assertThat(lexer.lexicalSplit("3*-5/2")).containsExactly("3", "*", "-5", "/", "2");
    }

    @Test
    public void should_split_multiple_operations(){
        assertThat(lexer.lexicalSplit("3/5+2-3*6/5-6")).containsExactly("3", "/", "5", "+", "2", "-", "3", "*", "6", "/", "5", "-", "6");
    }

    @Test
    public void should_split_when_numbers_greater_than_ten(){
        assertThat(lexer.lexicalSplit("3/-5+2-3*65/5-68")).containsExactly("3", "/", "-5", "+", "2", "-", "3", "*", "65", "/", "5", "-", "68");
    }



}