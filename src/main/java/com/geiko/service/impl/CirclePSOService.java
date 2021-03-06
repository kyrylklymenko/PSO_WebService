package com.geiko.service.impl;

import com.geiko.model.Answer;
import com.geiko.model.Properties;
import com.geiko.model.ProtocolRow;
import com.geiko.model.functions.Function;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class CirclePSOService {

    Function f;

    private String function;
    private int n = 1;
    private int d, s, gbest, old, nf, k;
    private double w = 0.712;
    private double c1 = 1.193;
    private double c2 = 1.193;
    private double r1, r2, e;
    private double[][] x;
    private double[][] v;
    private double maxSearchSpace;
    private double[][] p;
    private double[] xp;
    private double[] fp;
    private double[] fitnes;
    private double[] xmin;
    private double[] xmax;
    private int maxIterationCount = 10000;
    private int stopCause = 0;
    double xCharts[][];


    public double getMaxSearchSpace() {
        return maxSearchSpace;
    }

    public void setMaxSearchSpace(double maxSearchSpace) {
        this.maxSearchSpace = maxSearchSpace;
    }

    public Function getF() {
        return f;
    }

    public void setF(Function f) {
        this.f = f;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
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

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setProperties(Properties properties) {
        w = properties.getW();
        c1 = properties.getKogn();
        c2 = properties.getSoc();
        n = properties.getN();
        maxIterationCount = properties.getK();
        stopCause = properties.getStopCause();
    }


    private void particleSwarmInitialization() {     /*?????????????????????????? ???????????????? ??????*/
        x = new double[s][d];
        v = new double[s][d];
        p = new double[s][d];
        xp = new double[d];
        fp = new double[s];
        fitnes = new double[s];
        xmin = new double[d];
        xmax = new double[d];
        nf = 0;
        k = 0;
        f.setD(getD());
        for (int i = 0; i < d; i++) {
            xmin[i] = -maxSearchSpace;
            xmax[i] = maxSearchSpace;
        }
        Random rand = new Random();
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < d; j++) {
                x[i][j] = (((double) (rand.nextInt((int) (xmax[j] * 100)))) / 100d) + xmin[j];
                v[i][j] = ((((double) (rand.nextInt((int) (xmax[j] * 100)))) / 100d) + xmin[j] - x[i][j]) / 2;
                p[i][j] = x[i][j];
                xp[j] = x[i][j];
            }
            fitnes[i] = f.getResult(xp);
            fp[i] = fitnes[i];
        }
    }
    private void searchInitialBestParticle(){
        gbest = 0;
        old = 0;
        for (int i = 1; i < s; i++) {
            if (fp[i] < fp[gbest]) gbest = i;
        }
    }
    private void refreshCurrentParticleBest(){
        for (int i = 0; i < s; i++) {      /*?????????????????? ?????????????????? ???????????????????? ?????????????? ???????????????? ??????*/
            if (fitnes[i] < fp[i]) {
                fp[i] = fitnes[i];
                for (int j = 0; j < d; j++)
                    p[i][j] = x[i][j];
            }
        }
    }
    private void refreshCurrentSwarmBest(){
        for (int i = 0; i < s; i++) {  /*?????????????????? ?????????????????? ???????????????? ??????*/
            if (fp[i] < fp[gbest]) {
                old = gbest;
                gbest = i;
            }
        }
    }

    public Answer solve() {
        Answer answer = new Answer();
        particleSwarmInitialization();
        searchInitialBestParticle();
        boolean end = false;
        do {
            k++;
            refreshSpeedAndPosition();
            if (d == 2)
                answer.getDataForX().add(xCharts);
            refreshCurrentParticleBest();
            refreshCurrentSwarmBest();
            end=isEndExecution();
            ArrayList<Double> x = new ArrayList<>();
            for (int i = 0; i < d; i++) {
                x.add(p[gbest][i]);
            }
            ProtocolRow row = new ProtocolRow(k, x, fp[gbest], Math.abs(fp[old] - fp[gbest]));
            answer.getProtocol().add(row);
            answer.getDataForCharts().add(fp[gbest]);
        } while (!end);
        if (k >= maxIterationCount) {
            answer.setStopCause("???????????????????? ?????????????????????? ?????????????????? ???????????????? k = " + maxIterationCount);
        } else {
            answer.setStopCause("?????????????? ???? ???????????? ?????? ???????????? ???????????? ?????????????????? ?? ???????????????? ?????????? ???? ?? = " + e + ".");
        }
        answer.setX(p[gbest]);
        answer.setF(fp[gbest]);
        answer.setNf(nf);
        answer.setK(k);
        return answer;
    }

    private void refreshSpeedAndPosition() {
        Random rand = new Random();
        xCharts= new double[s][d];
        int neighbour1;
        int neighbour2;
        int lbest;
        double fmin;
        for (int i = 0; i < s; i++) {
            lbest = i;
            fmin= fp[i];
            neighbour1 = i-1;
            neighbour2 = i+1;
            if (i==0) neighbour1 = s-1;
            if (i==s-1) neighbour2 = 0;
            if (fp[neighbour1] < fmin) { fmin = fp[neighbour1]; lbest =neighbour1;}
            if (fp[neighbour2] < fmin) {lbest = neighbour2;}
            for (int j = 0; j < d; j++) {
                r1 = rand.nextDouble();
                r2 = rand.nextDouble();
                v[i][j] = w * v[i][j] + c1 * r1 * (p[i][j] - x[i][j]);
                if (i != lbest)
                    v[i][j] = v[i][j] + c2 * r2 * (p[lbest][j] - x[i][j]);
                x[i][j] = x[i][j] + v[i][j];
                checkOutOfRange(i,j);
                xp[j] = x[i][j];
                if (d == 2)
                    xCharts[i][j] = x[i][j];
            }
            fitnes[i] = f.getResult(xp);
            nf++;
        }
    }

    private boolean isEndExecution() {
        boolean end = false;
        switch (stopCause) {
            case 0:
                if ((old != gbest) && (Math.abs(fp[old] - fp[gbest]) < e) || (k >= maxIterationCount)) {  /*?????????? ???????????? ?? ??????????*/
                    end = true;
                }
                break;
            case 1:
                if (k >= maxIterationCount) {
                    end = true;
                }
                break;
            case 2:
                if ((old != gbest) && (Math.abs(fp[old] - fp[gbest]) < e)) {
                    end = true;
                }
                break;
        }
        return end;
    }

    private void checkOutOfRange(int i, int j) {
        if (x[i][j] < xmin[j]) {     /*?????????????????? ???????????? ???????????????? ???? ???????? ???????????????? ????????????*/
            x[i][j] = xmin[j];
            v[i][j] = 0;
        }
        if (x[i][j] > xmax[j]) {
            x[i][j] = xmax[j];
            v[i][j] = 0;
        }
    }
}

