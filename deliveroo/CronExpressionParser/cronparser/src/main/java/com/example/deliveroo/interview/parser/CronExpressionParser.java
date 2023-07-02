package com.example.deliveroo.interview.parser;
import java.util.LinkedHashMap;
import java.util.Map;

public class CronExpressionParser {

    public Map<String, String> parseCronExpression(String input) {
        Map<String, String> result = new LinkedHashMap<>();
        String[] expressions = input.split(" ");
        if (expressions.length != 6) {
            System.err.println("Invalid cron string. Please provide all 5 time fields plus the command.");
            System.exit(1);
        }
        result.put("minute", parseMinuteExpression(expressions[0]));
        result.put("hour", parseHourExpression(expressions[1]));
        result.put("day of month", parseDayOfMonthExpression(expressions[2]));
        result.put("month", parseMonthExpression(expressions[3]));
        result.put("day of week", parseDayOfWeekExpression(expressions[4]));        
        result.put("command", parseCommandExpression(expressions[5]));
        return result;
    }

    private String parseMinuteExpression(String string) {
        return new MinuteExpressionParser().parseExpression(string);
    }

    private String parseCommandExpression(String string) {
        return new CommandExpressionParser().parseExpression(string);
    }

    private String parseDayOfWeekExpression(String string) {
        return new DayOfWeekExpressionParser().parseExpression(string);
    }

    private String parseMonthExpression(String string) {
        return new MonthExpressionParser().parseExpression(string);
    }

    private String parseDayOfMonthExpression(String string) {
        return new DayOfMonthExpressionParser().parseExpression(string);
    }

    private String parseHourExpression(String string) {
        return new HourExpressionParser().parseExpression(string);
    }

    public void parseIntoTableFormat(Map<String, String> result) {
        StringBuilder builder = new StringBuilder();
        for(String key : result.keySet()) {
            String formatted = String.format("%-14s", key);
            builder.append(formatted);
            builder.append(" ");
            builder.append(result.get(key));
            builder.append("\n");
        }
        System.out.println(builder.toString().trim());
    }
    
}
