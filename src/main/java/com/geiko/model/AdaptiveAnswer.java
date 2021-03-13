package com.geiko.model;

import java.util.ArrayList;

/**
 * Created by Андрей on 09.05.2017.
 */
public class AdaptiveAnswer extends Answer {
    protected double x [];
    protected double f;
    protected int k;
    protected int nf;
    protected ArrayList<ProtocolRow> protocol = new ArrayList<>();
    protected ArrayList<Double> dataForCharts = new ArrayList<>();
    protected String stopCause;
    protected int n;


    public ArrayList<double [][]> dataForX = new ArrayList<>();

    public int getN() {
        return n;
    }

    public ArrayList<double[][]> getDataForX() {
        return dataForX;
    }

    public void setDataForX(ArrayList<double[][]> dataForX) {
        this.dataForX = dataForX;
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

    public ArrayList<Double> getDataForCharts() {
        return dataForCharts;
    }

    public void setDataForCharts(ArrayList<Double> dataForCharts) {
        this.dataForCharts = dataForCharts;
    }

    public ArrayList<ProtocolRow> getProtocol() {
        return protocol;
    }

    public void setProtocol(ArrayList<ProtocolRow> protocol) {
        this.protocol = protocol;
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
    }
}
