package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class EllipticalFunction extends Function {
    @Override
    public double getResult(double[] x) {
        double sum = 0;
        for(int i = 0; i < d; i++){
            sum+=Math.pow(Math.pow(10,6),((i)/(d-1)))*Math.pow(x[i],2);
        }
        return sum;
    }
}
