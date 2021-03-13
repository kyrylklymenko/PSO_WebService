package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class WeierstrassFunction extends Function {
    @Override
    public double getResult(double[] x) {
        double sum1 = 0;
        for (int i = 0; i < d;i++){
            for (int k = 0;k<=20;k++){
                sum1+= Math.pow(0.5,k)*Math.cos(2*Math.PI*Math.pow(3,k)*(x[i]+0.5));
            }
        }
        double sum2 = 0;
        for (int k = 0; k <= 20;k++){
            sum2+=Math.pow(0.5,k)*Math.cos(Math.PI*Math.pow(3,k));
        }
        return (1.0/d)*sum1-sum2;
    }
}
