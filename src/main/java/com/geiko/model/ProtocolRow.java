package com.geiko.model;

import java.util.ArrayList;

/**
 * Created by Андрей on 23.05.2017.
 */
public class ProtocolRow {
    private int k;
    private ArrayList<Double> x = new ArrayList<>();
    private double f;
    private double e;

    public ProtocolRow(int k, ArrayList<Double> x, double f, double e) {
        this.k = k;
        this.x = x;
        this.f = f;
        this.e = e;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public ArrayList<Double> getX() {
        return x;
    }

    public void setX(ArrayList<Double> x) {
        this.x = x;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }
}
