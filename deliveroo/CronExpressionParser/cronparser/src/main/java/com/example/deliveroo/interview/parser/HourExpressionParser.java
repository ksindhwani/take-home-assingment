package com.example.deliveroo.interview.parser;

public class HourExpressionParser extends ExpressionParser{

     public final int MAX_VALUE = 23;
     public final int MIN_VALUE = 0;
     public final int MOD = 24;

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
       return MAX_VALUE + 1;
    }

    @Override
    public void invalidValueInCronString() {
        System.err.println("Invalid cron string for hour. Please provide valid hour string.");
        System.exit(1);
    }

    @Override
    public Integer getSkipValue(int value) {
        return (value%MOD);
    }
    
}
