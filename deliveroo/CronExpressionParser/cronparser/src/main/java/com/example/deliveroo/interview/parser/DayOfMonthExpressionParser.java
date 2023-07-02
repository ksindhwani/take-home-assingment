package com.example.deliveroo.interview.parser;

public class DayOfMonthExpressionParser extends ExpressionParser{

    public final int MAX_VALUE = 31;
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
    public void invalidValueInCronString() {
        System.err.println("Invalid cron string for day of month. Please provide valid day of month string.");
        System.exit(1);
    }

    @Override
    public Integer getSkipValue(int value) {
        return value;
    }

    @Override
    public int getMinValueForSkipExpression() {
        return MIN_VALUE;
    }

    @Override
    public int getMaxValueForSkipExpression() {
         return MAX_VALUE;
    }    
}
