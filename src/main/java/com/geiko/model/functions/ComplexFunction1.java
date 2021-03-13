package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */

public class ComplexFunction1 extends Function {
    @Override
    public double getResult(double[] x) {
        double result = 4*Math.pow(x[0],2)-2.1*Math.pow(x[0],4)+1/3*Math.pow(x[0],6)+x[0]*x[1]-4*Math.pow(x[1],2)+4*Math.pow(x[1],4);
        return result;
    }
}
