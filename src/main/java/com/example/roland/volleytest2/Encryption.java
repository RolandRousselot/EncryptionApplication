package com.example.roland.volleytest2;

import java.util.Arrays;

public class Encryption {

    private boolean[] StringToASCII(String message){

        boolean[] returnVal = new boolean[message.length()*8];

        byte[] bytes = message.getBytes();

        for(int letter = 0; letter < message.length(); letter ++){


            for(int digit = 0; digit < 8; digit++ ){

                returnVal[8*letter + 7 - digit] = ( (int) Math.pow(2, digit) & bytes[letter]) != 0;

            }

        }

        return returnVal;

    }

    public double[] Encrypt(String message){

        boolean[] Binary_Digits = StringToASCII(message);

        int Time_Per_Digit = 10;
        int Time_Steps = Binary_Digits.length*100*Time_Per_Digit;
        double[] Encrypted_Message = Runge_Kutta.Encrypt(Binary_Digits, Time_Steps);

        return Encrypted_Message;

    }

    public String Decrypt(double[] signal){

        double[] Difference = Runge_Kutta.Decrypt(signal);
        int Digit_Number = Difference.length/500;
        int[] ASCII = new int[Digit_Number];

        for(int Digit = 0; Digit < Digit_Number; Digit++ ){

            double[] vals = Arrays.copyOfRange(Difference, 50 + Digit*500,  250 + Digit*500);

            //System.out.println(Max.maximum(vals));

            if(Max.maximum(vals) < 1.5){

                ASCII[Digit] = 0;

            } else{

                ASCII[Digit] = 1;

            }

        }

        String returnString = ASCIIToString(ASCII);

        return returnString;

    }

    private String ASCIIToString(int[] ASCII){

        int Characters = ASCII.length/8;
        String returnString = "";

        for(int Letter = 0; Letter < Characters; Letter++ ){

            int[] Digits =  Arrays.copyOfRange(ASCII, Letter*8, 8 + Letter*8);

            int Sum = 0;

            for(int j = 0; j < 8; j++ ){

                Sum += Digits[j]*Math.pow(2, 7 - j);

            }

            returnString += Character.toString ((char) Sum);

        }

        return returnString;

    }

}