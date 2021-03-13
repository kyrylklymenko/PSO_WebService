package com.geiko.model;

import com.geiko.model.functions.Function;
import org.springframework.stereotype.Component;

/**
 * Created by Андрей on 09.05.2017.
 */

@Component
public class Portfolio2Problem {
    protected double e;
    protected int s;
    protected double Mp;
    protected double m1;
    protected double m2;



    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public double getMp() {
        return Mp;
    }

    public void setMp(double Mp) {
        this.Mp = Mp;
    }

    public double getm1() {
        return m1;
    }

    public void setm1(double m1) {
        this.m1 = m1;
    }

    public double getm2() {
        return m1;
    }

    public void setm2(double m2) {
        this.m2 = m2;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }
}
