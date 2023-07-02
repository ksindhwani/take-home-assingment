package com.example.practice.interview.Question1;

import java.util.ArrayList;
import java.util.List;

public class StringConverter {
    private StringMapper mapper;

    public StringConverter() {
        mapper =  new StringMapper();
        
    }

    public String convertToString(int number) {
        if(number == 0) {
            return "zero";
        }
        List<String> result =  new ArrayList<>();
        int place = 1;
        int remainderBy100 = number%100;
        int quotientBy100 = number/100;
        number = quotientBy100;
        if(remainderBy100 != 0) {
            result.add(mapper.getStringValue(remainderBy100, place));
        }
        place = place + 2;
        while(number != 0) {
            if(place > 3) {
                if(number%100 != 0) {
                    result.add(mapper.getStringValue(number%100, place));
                }
                number = number/100;
                place = place + 2;
            } else {
                if(number%10 != 0) {
                    result.add(mapper.getStringValue(number%10, place));
                }
                number =  number/10;;
                place++;
            }
        }
        return stringForm(result);
    }

    private String stringForm(List<String> result) {
        StringBuilder builder = new StringBuilder();
        for(int i= result.size()-1; i>=0 ; i--) {
            builder.append(result.get(i));
            builder.append(" ");
        }
        return builder.toString().stripTrailing();
    }
    
}
