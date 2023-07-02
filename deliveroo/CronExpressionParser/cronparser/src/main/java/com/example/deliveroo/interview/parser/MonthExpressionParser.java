package com.example.deliveroo.interview.parser;

public class MonthExpressionParser extends ExpressionParser{

    public final int MAX_VALUE = 12;
    public final int MIN_VALUE = 1;

    @Override
    public int getMinValue() {
        return MIN_VALUE;
    }

    @Override
    public int getMaxValue() {
        return MAX_VALUE;
    }

    @Override
    public int getMinValueForSkipExpression() {
        return MIN_VALUE;
    }

    @Override
    public int getMaxValueForSkipExpression() {
       return MAX_VALUE;
    }

    @Override
    public void invalidValueInCronString() {
        System.err.println("Invalid cron string for month. Please provide valid month string.");
        System.exit(1);
    }

    @Override
    public Integer getSkipValue(int value) {
        return value;
    }
    
}
