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
            int index = lexique.indexOf(operateur.getSign());
            while (index != -1) {
                lexique.remove(index);
                String operandeValueB = lexique.remove(index);
                String operandeValueA = lexique.remove(index - 1);
                Operande operandeA = getOperande(operandeValueA);
                Operande operandeB = getOperande(operandeValueB);

                Calcul calcul = new Calcul(operateur, operandeA, operandeB);
                calculs.add(calcul);
                if (!lexique.isEmpty()) {
                    lexique.add(lexique.size() >= index ? index-1 : lexique.size(), calcul.getId());
                }
                index = lexique.indexOf(operateur.getSign());
            }

        }
        if(!lexique.isEmpty()){
            throw new IllegalArgumentException("invalid input "+ Arrays.toString(lexique.toArray()));
        }
        return calculs;
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
//        for (int index = textCalcul.startsWith("-") ? 1 : 0; index < textCalcul.toCharArray().length; index++) {
//            Optional<Operateur> fromSign = Operateur.getFromSign(String.valueOf(textCalcul.charAt(index)));
//            if (fromSign.isPresent()) {
//                return createCalcul(fromSign.get(), textCalcul.substring(0, index), textCalcul.substring(index + 1));
//            }
//        }
//        throw new IllegalArgumentException("sign not found in " + textCalcul);
    //}

//    private Calcul createCalcul(final Operateur operateur, final String a,String b) {
//        Operande operandeA=null;
//        Operande operandeB;
//
//        if(numberRegexp.matcher(a).find()){
//            operandeA = new Operande(Integer.parseInt(a));
//        }
//        if(numberRegexp.matcher(b).find()){
//            operandeB = new Operande(Integer.parseInt(b));
//        }else{
//            operandeB = parseUnit(b);
//        }
//        return new Calcul(operateur, operandeA,operandeB);
//    }


}
