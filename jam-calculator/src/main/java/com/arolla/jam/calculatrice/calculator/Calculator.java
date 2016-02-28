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
    private Map<String, UUIDOperandTuple> resultsCache;

    public Calculator(Calcul calcul) {
        resultsCache = new HashMap<>();
        this.calcul = calcul;
    }

    public String calculate(String command) {
        return processWaitingCalculation(command).orElse(processDirectCalculation(command));
    }

    private Optional<String> processWaitingCalculation(String command) {
        final Matcher matcher = RESULT_PATTERN.matcher(command);
        if (matcher.find() && matcher.groupCount() == 2 && resultsCache.containsKey(matcher.group(1))) {
            final String resultKey = matcher.group(1);
            final UUIDOperandTuple uuidOperandTuple = resultsCache.get(resultKey);
            final String result = matcher.group(2);
            if (replaceOperandIfNecessary(resultKey, result)) {
                LOGGER.info("command valid, process Waiting Calculation...");
                return getCalculResult(uuidOperandTuple.uuid, uuidOperandTuple.operand, result);
            }
        }
        return Optional.empty();
    }

    private boolean replaceOperandIfNecessary(String resultKey, String result) {
        final UUIDOperandTuple uuidOperandTuple = resultsCache.get(resultKey);
        if (!isNumeric(uuidOperandTuple.operand)) {
            LOGGER.debug("calculation between two results...");
            resultsCache.put(uuidOperandTuple.operand, new UUIDOperandTuple(uuidOperandTuple.uuid, result));
            return false;
        }
        return true;
    }

    private String processDirectCalculation(String command) {
        final Matcher matcher = getCalculMatcher(command);
        if (matcher != null) {
            final String operationGroup = matcher.group(3);
            final String[] operands = operationGroup.split(OPERATION_SEPARATOR);
            if (isValid(operands)) {
                LOGGER.info("command valid, process Direct Calculation...");
                final String commandUUID = matcher.group(2);
                final String first = operands[0];
                final String second = operands[1];
                return getCalculResult(commandUUID, first, second).orElse("");
            }
        }
        LOGGER.warn("command ignored : {}", command);
        return "";
    }

    private Optional<String> getCalculResult(String commandUUID, String first, String second) {
        Optional<Integer> op1 = extractOperand(commandUUID, first, second);
        Optional<Integer> op2 = extractOperand(commandUUID, second, first);
        return op1.map(op -> {
            if (op2.isPresent()) {
                final String compute = calcul.compute(op, op2.get());
                resultsCache.put(commandUUID, new UUIDOperandTuple(commandUUID, compute));
                return RESULTAT + START_GROUP + commandUUID + END_GROUP + START_GROUP + compute + END_GROUP;
            }
            return null;
        });
    }

    private Optional<Integer> extractOperand(String commandUUID, String first, String second) {
        Optional<Integer> op1 = Optional.empty();
        if (resultsCache.containsKey(first)) {
            op1 = Optional.of(Integer.parseInt(resultsCache.get(first).operand));
        } else if (!isNumeric(first)) {
            resultsCache.put(first, new UUIDOperandTuple(commandUUID, second));
        } else {
            op1 = Optional.of(Integer.parseInt(first));
        }
        return op1;
    }

    private boolean isNumeric(String operandStr) {
        return operandStr.matches("-?\\d+");
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

    public Map<String, UUIDOperandTuple> getResultsCache() {
        return resultsCache;
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
