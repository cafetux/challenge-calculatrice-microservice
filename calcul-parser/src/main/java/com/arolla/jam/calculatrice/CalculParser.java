package com.arolla.jam.calculatrice;

import com.arolla.jam.calculatrice.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by cafetux on 22/01/2016.
 */
public class CalculParser {


    private Pattern numberRegexp = Pattern.compile("^-?\\d*$");
    private Lexer lexer = new Lexer();

    public List<Calcul> parse(final String calculText) {

        List<String> lexique = lexer.lexicalSplit(calculText);

        List<Calcul> calculs = new ArrayList<>();
        for (Operateur operateur : Operateur.values()) {
            int index = lexique.indexOf(operateur.signe());
            while (index != -1) {
                removeSign(lexique, index);
                Operande operandeB = getAndRemoveOperande(lexique, index);
                Operande operandeA = getAndRemoveOperande(lexique, index - 1);

                Calcul calcul = new Calcul(operateur, operandeA, operandeB);
                calculs.add(calcul);
                if (!lexique.isEmpty()) {
                    lexique.add(lexique.size() >= index ? index-1 : lexique.size(), calcul.getId());
                }
                index = lexique.indexOf(operateur.signe());
            }

        }
        if(!lexique.isEmpty()){
            throw new IllegalArgumentException("invalid input "+ Arrays.toString(lexique.toArray()));
        }
        return calculs;
    }

    private Operande getAndRemoveOperande(List<String> lexique, int index) {
        String operandeValue = lexique.remove(index);
        return getOperande(operandeValue);
    }

    private void removeSign(List<String> lexique, int index) {
        lexique.remove(index);
    }

    private Operande getOperande(String operandeValueA) {
        Operande operandeA;
        if(numberRegexp.matcher(operandeValueA).find()){
            operandeA=new IntegerOperande(Integer.parseInt(operandeValueA));
        }else{
            operandeA = new FutureOperande(operandeValueA);
        }
        return operandeA;
    }

}
