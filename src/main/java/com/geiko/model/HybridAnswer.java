package com.geiko.model;

/**
 * Created by Андрей on 09.06.2017.
 */
public class HybridAnswer extends Answer {
    protected double nmX [];
    protected double nmF;
    protected int nmK;
    protected int nmNf;

    public HybridAnswer(Answer answer) {
        this.x = answer.getX();
        this.f = answer.getF();
        this.k = answer.getK();
        this.nf = answer.getNf();
        this.protocol = answer.getProtocol();
        this.dataForCharts = answer.getDataForCharts();
        this.stopCause = answer.getStopCause();
        this.n = answer.n;
        this.dataForX = answer.getDataForX();
    }

    public double[] getNmX() {
        return nmX;
    }

    public void setNmX(double[] nmX) {
        this.nmX = nmX;
    }

    public double getNmF() {
        return nmF;
    }

    public void setNmF(double nmF) {
        this.nmF = nmF;
    }

    public int getNmK() {
        return nmK;
    }

    public void setNmK(int nmK) {
        this.nmK = nmK;
    }

    public int getNmNf() {
        return nmNf;
    }

    public void setNmNf(int nmNf) {
        this.nmNf = nmNf;
    }
}
