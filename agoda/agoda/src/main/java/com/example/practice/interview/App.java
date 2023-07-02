package com.example.practice.interview;

import com.example.practice.interview.Questiion2.MyHashMap;
import com.example.practice.interview.Question1.StringConverter;

/**
 * 
 * Question 1
 * 
 * Convert a number into a String
 * Example 47 - Forty Seven
 * 
 * Numbers will be atmax 6 six digts
 * 1- 999999
 * 
 * 
 * 
 * Question 2
 * Design your own Hashmap
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Question 1

        /*int number = 319;
        String numberString = new StringConverter().convertToString(number);
        System.out.println(numberString);*/


        // Question 2
        MyHashMap<Integer,String> mymap = new MyHashMap<>();
        mymap.put(15, "Kunall");
        mymap.put(16, "Karan");
        System.out.println(mymap.get(15));
        System.out.println(mymap.get(16));
        System.out.println(mymap.get(17));
    }
}
