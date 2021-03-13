package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class ComplexFunction2 extends Function {
    @Override
    public double getResult(double[] x) {
        double result =Math.pow((x[1]-5.1/4/Math.PI/Math.PI*x[0]*x[0]+5/ Math.PI*x[0]-6),2)+10*(1-1/8/Math.PI)*Math.cos(x[0])+10;
        return result;
    }
}