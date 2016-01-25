package com.arolla.jam.calculatrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cafetux on 24/01/2016.
 */
public class Lexer {

    public List<String> lexicalSplit(String calcul){
        List<String> strings = new ArrayList<>();
        int start=0;
        for (int index=1;index<=calcul.length();index++){
            if(!calcul.substring(start,index).matches("^-?[0-9]*$")){
                strings.add(calcul.substring(start,index-1));
                strings.add(String.valueOf(calcul.charAt(index-1)));
                start=index;
            }
        }
        strings.add(calcul.substring(start));

        return strings;
    }

}
