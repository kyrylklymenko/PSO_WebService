package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class Schwefel2Function extends Function{

    @Override
    public double getResult(double[] x) {
        double sum=0;
        for (int i = 0; i <d;i++){
            double sum2 = 0;
            for (int j = 0; j <= i; j++){
                sum2+=x[j];
            }
            sum+=Math.pow(sum2,2);
        }
        return sum;
    }
}
