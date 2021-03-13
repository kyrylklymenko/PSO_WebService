package com.geiko.model;

import java.util.ArrayList;

/**
 * Created by Андрей on 09.05.2017.
 */
public class Portfolio2Answer extends Answer {
    protected double x [];
    protected double f;
    protected int k;
    protected int nf;
    protected String stopCause;
    protected int n;

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getStopCause() {
        return stopCause;
    }

    public void setStopCause(String stopCause) {
        this.stopCause = stopCause;
    }

    public double[] getX() {
        return x;
    }

    public void setX(double[] x) {
        this.x = x;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getNf() {
        return nf;
    }

    public void setNf(int nf) {
        this.nf = nf;
    }}