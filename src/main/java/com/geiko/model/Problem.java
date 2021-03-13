package com.geiko.model;

import com.geiko.model.functions.Function;
import org.springframework.stereotype.Component;

/**
 * Created by Андрей on 09.05.2017.
 */

@Component
public class Problem {
    protected String function;
    protected boolean testFunction;
    protected double e;
    protected int d;
    protected int s;
    protected double maxSearchSpace;
    protected Function f;

    public Function getF() {
        return f;
    }

    public void setF(Function f) {
        this.f = f;
    }

    public boolean isTestFunction() {
        return testFunction;
    }

    public void setTestFunction(boolean testFunction) {
        this.testFunction = testFunction;
    }

    public double getMaxSearchSpace() {
        return maxSearchSpace;
    }

    public void setMaxSearchSpace(double maxSearchSpace) {
        this.maxSearchSpace = maxSearchSpace;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
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
}
