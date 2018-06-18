package com.example.roland.volleytest2;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Translation {

    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }

    public static String DoublesToString(double[] doubles){

        char[] charArray = new char[doubles.length*8];

        for(int i = 0; i < doubles.length; i++ ){

            byte[] bytes = toByteArray(doubles[i]);

            for(int j = 0; j < 8; j++ ){

                charArray[8*i + j] = (char) bytes[j];

            }

        }

        return new String(charArray);

    }

    public static double[] StringToDoubles(String message){

        byte[] bytes = new byte[message.length()];
        char[] chars = message.toCharArray();

        for(int i = 0; i < bytes.length; i++ ){

            bytes[i] = (byte) chars[i];
        }

        double[] returnVal = new double[bytes.length/8];

        for(int l = 0; l < returnVal.length; l++ ){

            byte[] double_bytes = Arrays.copyOfRange(bytes, l*8, 8 + l*8);

            returnVal[l] = toDouble(double_bytes);

        }

        return returnVal;

    }

}
