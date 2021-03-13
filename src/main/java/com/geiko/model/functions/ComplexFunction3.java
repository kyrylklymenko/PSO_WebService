package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class ComplexFunction3 extends Function {
    @Override
    public double getResult(double[] x) {
        double result =(1+(x[0]+x[1]+1)*(x[0]+x[1]+1)*(19-14*x[0]+3*x[0]*x[0]-14*x[1]+6*x[1]*x[0]+3*x[1]*x[1]))*(30+(2*x[0]-3*x[1])*(2*x[0]-3*x[1])*(18-32*x[0]+12*x[0]*x[0]+48*x[1]-36*x[0]*x[1]+27*x[1]));
        return result;
    }
}