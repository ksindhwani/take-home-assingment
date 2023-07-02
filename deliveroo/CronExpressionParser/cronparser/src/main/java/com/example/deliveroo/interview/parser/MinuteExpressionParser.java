package com.example.deliveroo.interview.parser;

public class MinuteExpressionParser extends ExpressionParser{

    public final int MAX_VALUE = 59;
    public final int MIN_VALUE = 0;
    public final int MOD = 60;


    @Override
    public int getMinValue() {
        return MIN_VALUE;
    }

    @Override
    public int getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    public void invalidValueInCronString() {
        System.err.println("Invalid cron string for minute. Please provide valid minute string.");
        System.exit(1);
    }

    @Override
    public Integer getSkipValue(int value) {
        return (value%MOD);
    }

    @Override
    public int getMinValueForSkipExpression() {
        return MIN_VALUE;
    }

    @Override
    public int getMaxValueForSkipExpression() {
       return MAX_VALUE + 1;
    }
}
