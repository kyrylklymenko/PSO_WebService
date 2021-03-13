package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class RosenbrokeFunction extends Function {
    @Override
    public double getResult(double[] x) {
        double sum = 0;
        for (int i = 0; i< d-1;i++){
            sum +=100*Math.pow((x[i+1]-Math.pow((x[i]),2)),2)+Math.pow((1-x[i]),2);
        }
        return sum;
    }
}
