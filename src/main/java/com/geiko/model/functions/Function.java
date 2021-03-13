package com.geiko.model.functions;

/**
 * Created by Андрей on 18.05.2017.
 */
public abstract class Function {
    protected int d;

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public abstract double getResult(double[] x);
}
