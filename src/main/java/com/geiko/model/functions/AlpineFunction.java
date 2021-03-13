package com.geiko.model.functions;


import static java.lang.Math.*;

/**
 * Created by Андрей on 18.05.2017.
 */
public class AlpineFunction extends Function {
    @Override
    public double getResult(double[] x) {
        double sum = 0;
        for (int i = 0; i<d; i++){
            sum+=Math.abs((x[i]*Math.sin(x[i]))+(x[i]*0.1));
        }
        return sum;
    }
}
