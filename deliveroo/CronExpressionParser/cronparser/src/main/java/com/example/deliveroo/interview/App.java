package com.example.deliveroo.interview;

import java.util.Map;

import com.example.deliveroo.interview.parser.CronExpressionParser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       if (args.length != 1) {
            System.err.println("Please provide a cron string as an argument.");
            System.exit(1);
        }

        CronExpressionParser parser = new CronExpressionParser();
        String input = args[0];
        Map<String, String> result = parser.parseCronExpression(input);
        parser.parseIntoTableFormat(result);
    }
}
