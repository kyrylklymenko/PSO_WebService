package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public class GrivonkFunction extends Function {
    @Override
    public double getResult(double[] x) {
        double sum = 0;
        double comp=1;
        for(int i =0;i<d;i++){
            sum+=Math.pow(x[i],2);
            comp*=Math.cos(x[i]/Math.sqrt(i+1));
        }
        double result = 1+sum/4000.0-comp;
        return result;
    }
}
