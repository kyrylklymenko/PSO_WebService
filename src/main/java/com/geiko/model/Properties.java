package com.geiko.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Андрей on 29.05.2017.
 */
@Component
public class Properties {
    private double w;
    private double kogn;
    private double soc;
    private int k;
    private int d;
    private int s;
    private int n;
    private int stopCause;



    @Autowired
    public Properties() {
        this.w = 0.712;
        this.kogn = 1.193;
        this.soc = 1.193;
        this.k = 10000;
        this.s=100;
        this.d=50;
        this.n=1;
        this.stopCause = 0;
    }

    public int getStopCause() {
        return stopCause;
    }

    public void setStopCause(int stopCause) {
        this.stopCause = stopCause;
    }
    public double getW() {
        return w;
    }

    public void setW(double w) {
        this.w = w;
    }

    public double getKogn() {
        return kogn;
    }

    public void setKogn(double c1) {
        this.kogn = c1;
    }

    public double getSoc() {
        return soc;
    }

    public void setSoc(double c2) {
        this.soc = c2;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getD() {
        return d;
    }

    public void setD(int d) {
        this.d = d;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
