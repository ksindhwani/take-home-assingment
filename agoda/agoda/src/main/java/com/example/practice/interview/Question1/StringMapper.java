package com.example.practice.interview.Question1;

import java.util.HashMap;
import java.util.Map;

public class StringMapper {
    private Map<Integer,String> stringRepresentationMap;
    public StringMapper() {
        
        stringRepresentationMap = new HashMap<>();
        stringRepresentationMap.put(1, "one");
        stringRepresentationMap.put(2, "rwo");
        stringRepresentationMap.put(3, "three");
        stringRepresentationMap.put(4, "four");
        stringRepresentationMap.put(5, "five");
        stringRepresentationMap.put(6, "six");
        stringRepresentationMap.put(7, "seven");
        stringRepresentationMap.put(8, "eight");
        stringRepresentationMap.put(9, "nine");

        stringRepresentationMap.put(11, "eleven");
        stringRepresentationMap.put(12, "twelve");
        stringRepresentationMap.put(13, "thirteen");
        stringRepresentationMap.put(14, "fourteen");
        stringRepresentationMap.put(15, "fifteen");
        stringRepresentationMap.put(16, "sixteen");
        stringRepresentationMap.put(17, "seventeen");
        stringRepresentationMap.put(18, "eighteen");
        stringRepresentationMap.put(19, "nineteen");

        stringRepresentationMap.put(10, "ten");
        stringRepresentationMap.put(20, "twenty");
        stringRepresentationMap.put(30, "thirty");
        stringRepresentationMap.put(40, "forty");
        stringRepresentationMap.put(60, "fifty");
        stringRepresentationMap.put(60, "sixty");
        stringRepresentationMap.put(70, "seventy");
        stringRepresentationMap.put(80, "eighty");
        stringRepresentationMap.put(90, "ninety");
    }

    public String getStringValue(int number, int place) {
        if(number >= 1 && number <=99 && place == 1) {
            return computerStringRepresentation(number);
        }
        else {
            switch(place) {
                case 1: return stringRepresentationMap.get(number);
                case 2: return stringRepresentationMap.get(number*10);
                case 3: return stringRepresentationMap.get(number) + " hundred";
                case 4: 
                case 5: return computerStringRepresentation(number) + " thousand";
                case 6: return stringRepresentationMap.get(number) + " lakh";
                }   
            }
        return null;
    }

    private String computerStringRepresentation(int number) {
        StringBuilder builder = new StringBuilder();
        if(stringRepresentationMap.containsKey(number)) {
            builder.append(stringRepresentationMap.get(number));
        } else {
            builder.append(stringRepresentationMap.get(number - number%10));
            builder.append(" ");
            builder.append(stringRepresentationMap.get(number%10));
        }
        return builder.toString().stripTrailing();
    }
}
