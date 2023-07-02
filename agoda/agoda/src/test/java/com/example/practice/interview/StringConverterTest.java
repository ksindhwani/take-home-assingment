package com.example.practice.interview;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.example.practice.interview.Question1.StringConverter;

public class StringConverterTest {

    StringConverter converter;
    
    @Before
    public void setup() {
        converter = new StringConverter();
    }

    @Test
    public void testConvertToString_10() {
        int number = 10;
        String result =  "ten";
        assertEquals(result, converter.convertToString(number));
    }

    @Test
    public void testConvertToString_98() {
        int number = 98;
        String result =  "ninety eight";
        assertEquals(result, converter.convertToString(number));
    }

    @Test
    public void testConvertToString_30() {
        int number = 30;
        String result =  "thirty";
        assertEquals(result, converter.convertToString(number));
    }

    @Test
    public void testConvertToString_9845() {
        int number = 9845;
        String result =  "nine thousand eight hundred forty five";
        assertEquals(result, converter.convertToString(number));
    }

    @Test
    public void testConvertToString_191919() {
        int number = 19019;
        String result =  "nineteen thousand nineteen";
        assertEquals(result, converter.convertToString(number));
    }

    @Test
    public void testConvertToString_0() {
        int number = 0;
        String result =  "zero";
        assertEquals(result, converter.convertToString(number));
    }

    @Test
    public void testConvertToString_100() {
        int number = 100;
        String result =  "one hundred";
        assertEquals(result, converter.convertToString(number));
    }

    @Test
    public void testConvertToString_110() {
        int number = 110;
        String result =  "one hundred ten";
        assertEquals(result, converter.convertToString(number));
    }

    @Test
    public void testConvertToString_100001() {
        int number = 100001;
        String result =  "one lakh one";
        assertEquals(result, converter.convertToString(number));
    }
}
