package com.example.roland.volleytest2;

public class Runge_Kutta {

    public static double[] Encrypt(boolean[] Binary_Digits, int Time_Steps){

        double[] x = {1, 1, 1};
        double h = 0.01;
        double[] returnArray = new double[Time_Steps];

        for(int Step = 0; Step < Time_Steps; Step++ ){

            returnArray[Step] = x[0];

            double bin = (Binary_Digits[Step/1000])?1.0:0.0;

            double[] k1 = MultArray(f(x, bin), h);

            double[] x2 = SumArray(x, DivArray(k1, 2));
            double[] k2 = MultArray(f(x2, bin), h);

            double[] x3 = SumArray(x, DivArray(k2, 2));
            double[] k3 = MultArray(f(x3, bin), h);

            double[] x4 = SumArray(x, k3);
            double[] k4 = MultArray(f(x4, bin), h);

            double[] difference_vec = SumArray(SumArray(k1, MultArray(k2, 2)), SumArray(MultArray(k3, 2), k4));

            x = SumArray(x, DivArray(difference_vec, 6));

        }

        return returnArray;

    }

    public static double[] Decrypt(double[] signal){

        double[] x = {1, 1, 1};
        double h = 0.02;
        double[] Difference = new double[signal.length/2];
        double[] x_vals = new double[Difference.length];

        for(int Step = 0; Step < Difference.length - 1; Step++ ){

            x_vals[Step] = x[0];
            Difference[Step] = Math.abs(x[0] - signal[Step*2]);

            double x_signal = signal[Step*2];
            double[] k1 = MultArray(g(x_signal, x), h);

            double x_signal2 = signal[1 + Step*2];
            double[] x2 = SumArray(x, DivArray(k1, 2));
            double[] k2 = MultArray(g(x_signal2, x2), h);

            double x_signal3 = signal[1 + Step*2];
            double[] x3 = SumArray(x, DivArray(k2, 2));
            double[] k3 = MultArray(g(x_signal3, x3), h);


            double x_signal4 = signal[2 + Step*2];
            double[] x4 = SumArray(x, k3);
            double[] k4 = MultArray(g(x_signal4, x4), h);

            double[] difference_vec = SumArray(SumArray(k1, MultArray(k2, 2)), SumArray(MultArray(k3, 2), k4));

            x = SumArray(x, DivArray(difference_vec, 6));

        }

        Difference[Difference.length - 1] = 0;

        return Difference;

    }

    private static double[] f(double[] x, double bin){

        final double sigma = 16;
        final double ro = 45.2;
        final double beta = 4;
        final double delta = 10;

        double[] returnArray = new double[3];

        returnArray[0] = (sigma + delta*bin)*(x[1] - x[0]);
        returnArray[1] =  x[0]*(ro - x[2]) - x[1];
        returnArray[2] = x[0]*x[1] - beta*x[2];

        return returnArray;

    }

    private static double[] g(double x_signal, double[] x){

        final double sigma = 16;
        final double ro = 45.2;
        final double beta = 4;

        double[] returnArray = new double[3];

        returnArray[0] = sigma*(x[1] - x[0]);
        returnArray[1] =  x_signal*(ro - x[2]) - x[1];
        returnArray[2] = x_signal*x[1] - beta*x[2];

        return returnArray;

    }

    private static double[] DivArray(double[] x, double a){

        double[] returnArray = new double[3];

        for(int i = 0; i < x.length; i++){

            returnArray[i] = x[i]/a;

        }

        return returnArray;

    }

    private static double[] SumArray(double[] x, double[] y){

        double[] returnArray1 = new double[3];

        for(int i = 0; i < x.length; i++ ){

            returnArray1[i] = x[i] + y[i];

        }

        return returnArray1;

    }

    private static double[] MultArray(double[] x, double a){

        double[] returnArray = new double[3];

        for(int i = 0; i < x.length; i++){

            returnArray[i] = x[i]*a;

        }

        return returnArray;

    }

}