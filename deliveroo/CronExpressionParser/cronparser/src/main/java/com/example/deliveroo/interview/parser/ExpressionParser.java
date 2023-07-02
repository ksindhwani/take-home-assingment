package com.example.deliveroo.interview.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class ExpressionParser {

    public abstract int getMinValue();
    public abstract int getMaxValue();
    public abstract int getMinValueForSkipExpression();
    public abstract int getMaxValueForSkipExpression();
    public abstract void invalidValueInCronString();
    public abstract Integer getSkipValue(int value);
    

    public List<Integer> fetchSkipValues(int start, int end, int skipValue) {
         if(isValidValues(start, end, skipValue)) {
            List<Integer> skipValues = new ArrayList<>();
            int value = start + skipValue;
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

    public String parseExpression(String string) {
        List<Integer> values = new ArrayList<>();
        
        // If the minute Expression is just a number
        if (isNumber(string)) {
            Integer value = Integer.parseInt(string);
            if(value >= getMinValue() && value <= getMaxValue()) {
                return value.toString();
            } else {
                 invalidValueInCronString();
            }

        // if the minute expression is "*"
        } else if(isStar(string)) {
            values = fetchRangeValues(getMinValue(), getMaxValue());
        
        // if the minute Expressino is of format "X-Y" where X and Y are integers 
        } else if(isRangeValues(string)) {
            String[] ranges = string.split("-");
            int start = Integer.parseInt(ranges[0]);
            int end = Integer.parseInt(ranges[1]);
            values = fetchRangeValues(start,end);

        // if the minutre expression is Comma Seperated Values "X,Y"
        } else if (isCommaSeperated(string)) {
            values = fetchCommaSeperatedValues(string);

        // if the minute expression has skip value format
        } else {
            int start = 0;
            int end = 0;
            String[] parts = string.split("/");
            if (parts.length != 2) {
                invalidValueInCronString();
            }

            // If the skip value format is of type "X/Y" where X and Y is integer
            if(isNumber(parts[0])) {
                start = Integer.parseInt(parts[0]);
                end  = getMaxValueForSkipExpression();

            // If the skip value format is of type "*/X" where X is integer
            } else if(isStar(parts[0])) {
                start = getMinValueForSkipExpression(); 
                end = getMaxValueForSkipExpression();

            // If the skip value format is of type "X-Y/Z" where X, Y, Z are integers
            } else if(isRangeValues(parts[0])) {
                String[] ranges = parts[0].split("-");
                start = Integer.parseInt(ranges[0]);
                end = Integer.parseInt(ranges[1]);
            
            // If the skip value format is of type "X,Y/Z" where X, Y, Z are integers
            } else if(isCommaSeperated(parts[0])) {
                String[] subparts = parts[0].split(",");
                values.add(Integer.parseInt(subparts[0]));
                start = Integer.parseInt(subparts[1]);
                end = getMaxValueForSkipExpression();
            } else {
                 invalidValueInCronString();
            }
            values.addAll(fetchSkipValues(start, end , Integer.parseInt(parts[1])));
        }
        Collections.sort(values);
        return formatValues(values);
    }

     protected boolean isValidValues(int start, int end, int skipValue) {
        return ((start >= getMinValue() && start <=  getMaxValue()) 
        && (end >= getMinValue() && end <= getMaxValueForSkipExpression()) 
        && (start <= end)
        && skipValue > 0);
    }

    protected boolean isNumber(String string) {
        return string.matches("-?\\d+(\\.\\d+)?");
    }

    protected boolean isStar(String string) {
        return string.compareTo("*") == 0;
    }

    protected  List<Integer> fetchCommaSeperatedValues(String string) {
        return Arrays.stream(string.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    protected  boolean isCommaSeperated(String string) {
        String commaSeperatedRegex = "\\d+,\\d+";
        Pattern pattern = Pattern.compile(commaSeperatedRegex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    protected  boolean isRangeValues(String string) {
        String rangeRegex = "\\d+-\\d+";
        Pattern pattern = Pattern.compile(rangeRegex);
        Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    protected  String formatValues(List<Integer> values) {
        StringBuilder builder = new StringBuilder();
        for(Integer value : values) {
            builder.append(value);
            builder.append(" ");
        }
        return builder.toString().trim();
    }

    protected List<Integer> fetchRangeValues(int start, int end) {
        if ((start >= getMinValue() && start <=  getMaxValue()) && (end >= getMinValue() && end <= getMaxValue()) && (start <= end)) {
            List<Integer> values = new ArrayList<>();
            for(int i = start; i<=end; i++) {
                values.add(i);
            }
            return values;
        } else {
            invalidValueInCronString();
            return null;
        }
    }
}
 