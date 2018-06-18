package com.example.roland.volleytest2;

public class Max {

    public static double maximum(double[] list){

        double maximum = 0;

        for(int i = 0; i < list.length; i++){

            if(list[i] > maximum){

                maximum = list[i];

            }

        }

        return maximum;

    }

}