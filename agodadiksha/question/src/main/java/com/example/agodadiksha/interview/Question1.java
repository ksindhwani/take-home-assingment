package com.example.agodadiksha.interview;


/**
 * Number of traingles that can be formed with given array
 */
public class Question1 {

    public int[] solution(int[] arr) {
        int[] result = new int[arr.length - 2];

        for(int i =0 ;i< result.length; i++) {
            result[i] = isTrainglePossible(arr[i], arr[i+1], arr[i+2]);
        }

        return result;
    }

    private int isTrainglePossible(int a, int b, int c) {
       return (a+b > c) &&  (a+c > b) &&  (b+c > a) ? 1 : 0; 

    }
    
}
