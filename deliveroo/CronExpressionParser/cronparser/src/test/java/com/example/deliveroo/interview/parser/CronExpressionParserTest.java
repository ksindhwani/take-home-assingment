package com.example.deliveroo.interview.parser;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class CronExpressionParserTest {

    private final String MINUTE = "minute";
    private final String HOUR = "hour";
    private final String DAY_OF_MONTH = "day of month";
    private final String MONTH = "month";
    private final String DAY_OF_WEEK = "day of week";
    private final String COMMAND = "command";

    CronExpressionParser parser;

    @Before
    public void setup() {
        parser =  new CronExpressionParser();
    }

    @Test
    public void testParseCronExpression_Case1() {
        String input = "5 4 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }
    
    @Test
    public void testParseCronExpression_Case2() {
        String input = "* 4 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = getRangeValues(0, 59);
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case3() {
        String input = "5 * 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String hourValues = getRangeValues(0, 23);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case4() {
        String input = "5 4 * 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfMonthValues = getRangeValues(1, 31);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case12() {
        String input = "5 4 3 * 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String monthValues = getRangeValues(1, 12);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case5() {
        String input = "5 4 3 2 * /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfWeekValues = getRangeValues(0, 6);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case6() {
        String input = "* * 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = getRangeValues(0, 59);
        String hourValues = getRangeValues(0, 23);
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case7() {
        String input = "* * 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = getRangeValues(0, 59);
        String hourValues = getRangeValues(0, 23);
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case8() {
        String input = "* * * 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = getRangeValues(0, 59);
        String hourValues = getRangeValues(0, 23);
        String dayOfMonthValues = getRangeValues(1, 31);
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case9() {
        String input = "* * * * 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = getRangeValues(0, 59);
        String hourValues = getRangeValues(0, 23);
        String dayOfMonthValues = getRangeValues(1, 31);
        String monthValues = getRangeValues(1, 12);
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month",monthValues , result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case10() {
        String input = "* * * * * /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = getRangeValues(0, 59);
        String hourValues = getRangeValues(0, 23);
        String dayOfMonthValues = getRangeValues(1, 31);
        String monthValues = getRangeValues(1, 12);
        String dayOfWeekValues = getRangeValues(0, 6);
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month",monthValues , result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case11() {
        String input = "10-50 4 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = getRangeValues(10, 50);
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case13() {
        String input = "5 3-16 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String hourValues = getRangeValues(3, 16);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case14() {
        String input = "5 4 4-27 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfMonthValues = getRangeValues(4, 27);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case15() {
        String input = "5 4 3 3-10 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String monthValues = getRangeValues(3, 10);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case16() {
        String input = "5 4 3 2 1-5 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfWeekValues = getRangeValues(1, 5);
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case17() {
        String input = "15,30 4 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = getCommaSeperatedValues("15,30");
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case18() {
        String input = "5 3,16 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String hourValues = getCommaSeperatedValues("3,16");
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case19() {
        String input = "5 4 4,27 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfMonthValues = getCommaSeperatedValues("4,27");
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case20() {
        String input = "5 4 3 3,10 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String monthValues = getCommaSeperatedValues("3,10");
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case21() {
        String input = "5 4 3 2 1,5 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfWeekValues = getCommaSeperatedValues("1,5");
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case22() {
        String input = "10/5 4 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = "0 15 20 25 30 35 40 45 50 55";
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case23() {
        String input = "5 5/4 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String hourValues = "9 13 17 21";
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case24() {
        String input = "5 4 5/4 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfMonthValues = "9 13 17 21 25 29";
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case25() {
        String input = "5 4 3 2/4 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String monthValues = "6 10";
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case26() {
        String input = "5 4 3 2 2/2 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfWeekValues = "0 3 5";
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case27() {
        String input = "*/10 4 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = "0 10 20 30 40 50";
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case28() {
        String input = "5 */4 3 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String hourValues = "0 4 8 12 16 20";
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case29() {
        String input = "5 4 */4 2 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String dayOfMonthValues = "5 9 13 17 21 25 29";
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", "2", result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case30() {
        String input = "5 4 3 */4 1 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String monthValues = "5 9";
        assertEquals("Minute", "5", result.get(MINUTE));
        assertEquals("Hour", "4", result.get(HOUR));
        assertEquals("Day of month", "3", result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", "1", result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case31() {
        String input = "10-20 5-9 4/6 * 3,7 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = "10 11 12 13 14 15 16 17 18 19 20";
        String hourValues = "5 6 7 8 9";
        String dayOfMonthValues = "10 16 22 28";
        String monthValues = "1 2 3 4 5 6 7 8 9 10 11 12";
        String dayOfWeekValues = "3 7";
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case32() {
        String input = "10,20 5-9 4/6 * 3,7 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = "10 20";
        String hourValues = "5 6 7 8 9";
        String dayOfMonthValues = "10 16 22 28";
        String monthValues = "1 2 3 4 5 6 7 8 9 10 11 12";
        String dayOfWeekValues = "3 7";
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }


    @Test
    public void testParseCronExpression_Case33() {
        String input = "*/15 3/5 4,20 2 * /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = "0 15 30 45";
        String hourValues = "8 13 18 23";
        String dayOfMonthValues = "4 20";
        String monthValues = "2";
        String dayOfWeekValues = "0 1 2 3 4 5 6";
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    @Test
    public void testParseCronExpression_Case34() {
        String input = "*/15 */4 */5 */3 */2 /usr/bin/find";
        Map<String,String> result = parser.parseCronExpression(input);
        String minuteValues = "0 15 30 45";
        String hourValues = "0 4 8 12 16 20";
        String dayOfMonthValues = "6 11 16 21 26 31";
        String monthValues = "4 7 10";
        String dayOfWeekValues = "0 1 3 5";
        assertEquals("Minute", minuteValues, result.get(MINUTE));
        assertEquals("Hour", hourValues, result.get(HOUR));
        assertEquals("Day of month", dayOfMonthValues, result.get(DAY_OF_MONTH));
        assertEquals("Month", monthValues, result.get(MONTH));
        assertEquals("day of week", dayOfWeekValues, result.get(DAY_OF_WEEK));
        assertEquals("command", "/usr/bin/find", result.get(COMMAND));
    }

    private String getCommaSeperatedValues(String string) {
        return string.replace(',', ' ');
    }

    private String getRangeValues(int start, int end) {
        StringBuilder builder = new StringBuilder();
        for(int i = start; i<= end; i++) {
            builder.append(i);
            builder.append(" ");
        }
        return builder.toString().trim();
    }
}
