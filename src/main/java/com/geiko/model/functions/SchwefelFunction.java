package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class SchwefelFunction extends Function {
    @Override
    public double getResult(double[] x) {
        double sum = 0;
        for (int i = 0;i<d;i++){
            sum+=(x[i]*Math.sin(Math.sqrt(Math.abs(x[i]))));
        }
        return -(1.0/d)*sum+418.983;
    }
}
