package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class AqleyFunction extends Function {
    @Override
    public double getResult(double[] x) {
        double sum1=0;
        double sum2=0;
        for (int i = 0;i<d;i++){
            sum1+=Math.pow(x[i],2);
            sum2+=Math.cos(2*Math.PI*x[i]);
        }
        double result = -20*Math.exp(-0.2*Math.sqrt((1.0/d)*sum1))-Math.exp((1.0/d)*sum2)+20+Math.E;
        return result;
    }
}
