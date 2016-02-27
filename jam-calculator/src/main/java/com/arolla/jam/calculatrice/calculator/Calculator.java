package com.arolla.jam.calculatrice.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    public static Pattern COMMAND_PATTERN = Pattern.compile("^\\[(\\w+)\\]\\[(\\w+)\\]\\[(\\w+)\\]\\[([^\\]]+)\\]$");

    public static final String OPERATION_SEPARATOR = ";";
    public static final String CALCUL = "CALCUL";

    public static final String END_GROUP = "]";
    public static final String START_GROUP = "[";
    public static final String RESULTAT = "[RESULTAT]";

    private final Calcul calcul;

    public Calculator(Calcul calcul) {
        this.calcul = calcul;
    }

    public String calculate(String command) {
        final Matcher matcher = getMatcher(command);
        if (matcher != null) {
            final String operationGroup = matcher.group(4);
            final String[] operands = operationGroup.split(OPERATION_SEPARATOR);
            if (isValid(operands)) {
                LOGGER.info("command valid, processing...");
                final String commandUUID = matcher.group(3);
                String result = getCalculResult(operands);
                return RESULTAT + START_GROUP + commandUUID + END_GROUP + START_GROUP + result + END_GROUP;
            }
        }
        LOGGER.error("command invalid : {}", command);
        return "";
    }

    private String getCalculResult(String[] operands) {
        int op1 = Integer.parseInt(operands[0]);
        int op2 = Integer.parseInt(operands[1]);
        return calcul.compute(op1, op2);
    }

    private boolean isValid(String[] operands) {
        return operands.length == 2;
    }

    private Matcher getMatcher(String command) {
        final Matcher matcher = COMMAND_PATTERN.matcher(command);
        if (matcher.find() && matcher.groupCount() == 4
                && CALCUL.equals(matcher.group(1)) && calcul.accept(matcher.group(2))) {
            return matcher;
        } else {
            return null;
        }
    }
}
