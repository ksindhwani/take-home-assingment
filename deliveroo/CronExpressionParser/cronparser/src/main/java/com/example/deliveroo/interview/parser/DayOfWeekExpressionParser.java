package com.example.deliveroo.interview.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayOfWeekExpressionParser extends ExpressionParser{

    public final int MAX_VALUE = 6;
    public final int MIN_VALUE = 0;
     public final int MOD = 7;


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
        System.err.println("Invalid cron string for day of week. Please provide valid day of week string.");
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

    @Override
    public List<Integer> fetchSkipValues(int start, int end, int skipValue) {
        if (isValidValues(start, end, skipValue)) {
            List<Integer> skipValues = new ArrayList<>();
            int value = start + skipValue-1;
            while (value <= end) {
                skipValues.add(getSkipValue(value));
                value += skipValue;
            }
            Collections.sort(skipValues);
            return skipValues;
        } else {
            invalidValueInCronString();
            return null;
        }
    }
}
