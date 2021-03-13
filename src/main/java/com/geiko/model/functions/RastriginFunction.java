package com.geiko.model.functions;


/**
 * Created by Андрей on 18.05.2017.
 */
public class RastriginFunction extends Function{
    @Override
    public double getResult(double[] x) {
        double sum = 0;
        for(int i = 0; i<d; i++){
            sum+=Math.pow(x[i],2)-(10.0*(Math.cos(2*Math.PI*x[i])))+10.0;
        }
        return sum;
    }
}
