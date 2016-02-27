package com.arolla.jam.calculatrice.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private final Logger LOGGER = LoggerFactory.getLogger(Calculator.class);

    public static Pattern COMMAND_PATTERN = Pattern.compile("^\\[CALCUL]\\[(\\w+)\\]\\[(\\w+)\\]\\[([^\\]]+)\\]$");
    public static Pattern RESULT_PATTERN = Pattern.compile("^\\[RESULTAT]\\[(\\w+)\\]\\[([^\\]]+)\\]$");

    public static final String OPERATION_SEPARATOR = ";";

    public static final String END_GROUP = "]";
    public static final String START_GROUP = "[";
    public static final String RESULTAT = "[RESULTAT]";

    private final Calcul calcul;
    private Map<String, UUIDOperandTuple> waitingResult;

    public Calculator(Calcul calcul) {
        waitingResult = new HashMap<>();
        this.calcul = calcul;
    }

    public String calculate(String command) {
        return processWaitingCalculation(command).orElse(processDirectCalcutation(command));
    }

    private Optional<String> processWaitingCalculation(String command) {
        final Matcher matcher = RESULT_PATTERN.matcher(command);
        if (matcher.find() && matcher.groupCount() == 2 && waitingResult.containsKey(matcher.group(1))) {
            final String resultKey = matcher.group(1);
            final UUIDOperandTuple uuidOperandTuple = waitingResult.get(resultKey);
            final String result = matcher.group(2);
            if (replaceOperandIfNecessary(resultKey, result)) {
                LOGGER.info("command valid, processing...");
                final String calculResult = getCalculResult(uuidOperandTuple.uuid, uuidOperandTuple.operand, result);
                return Optional.of(calculResult);
            }
        }
        return Optional.empty();
    }

    private boolean replaceOperandIfNecessary(String resultKey, String result) {
        final UUIDOperandTuple uuidOperandTuple = waitingResult.get(resultKey);
        if (!isNumeric(uuidOperandTuple.operand)) {
            LOGGER.debug("calculation between two results...");
            waitingResult.put(uuidOperandTuple.operand, new UUIDOperandTuple(uuidOperandTuple.uuid, result));
            return false;
        }
        return true;
    }

    private String processDirectCalcutation(String command) {
        final Matcher matcher = getCalculMatcher(command);
        if (matcher != null) {
            final String operationGroup = matcher.group(3);
            final String[] operands = operationGroup.split(OPERATION_SEPARATOR);
            if (isValid(operands)) {
                LOGGER.info("command valid, processing...");
                final String commandUUID = matcher.group(2);
                final String first = operands[0];
                final String second = operands[1];
                if (!canCalculate(commandUUID, first, second)) return "";
                return getCalculResult(commandUUID, first, second);
            }
        }
        LOGGER.warn("command ignored : {}", command);
        return "";
    }

    private boolean canCalculate(String commandUUID, String first, String second) {
        if (!isNumeric(first)) {
            waitingResult.put(first, new UUIDOperandTuple(commandUUID, second));
            return false;
        }
        if (!isNumeric(second)) {
            waitingResult.put(second, new UUIDOperandTuple(commandUUID, first));
            return false;
        }
        return true;
    }

    private boolean isNumeric(String operandStr) {
        return operandStr.matches("-?\\d+");
    }

    private String getCalculResult(String commandUUID, String first, String second) {
        int op1 = Integer.parseInt(first);
        int op2 = Integer.parseInt(second);
        return RESULTAT + START_GROUP + commandUUID + END_GROUP + START_GROUP + calcul.compute(op1, op2) + END_GROUP;
    }

    private boolean isValid(String[] operands) {
        return operands.length == 2;
    }

    private Matcher getCalculMatcher(String command) {
        final Matcher matcher = COMMAND_PATTERN.matcher(command);
        if (matcher.find() && matcher.groupCount() == 3 && calcul.accept(matcher.group(1))) {
            return matcher;
        } else {
            return null;
        }
    }

    public Map<String, UUIDOperandTuple> getWaitingResult() {
        return waitingResult;
    }

    private class UUIDOperandTuple {
        public final String uuid;
        public final String operand;

        public UUIDOperandTuple(String uuid, String operand) {
            this.uuid = uuid;
            this.operand = operand;
        }
    }
}
