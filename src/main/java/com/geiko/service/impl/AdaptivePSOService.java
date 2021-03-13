package com.geiko.service.impl;

import com.geiko.model.AdaptiveAnswer;
import com.geiko.model.AdaptiveProperties;
import com.geiko.model.ProtocolRow;
import com.geiko.model.functions.Function;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class AdaptivePSOService {

    Function f;

    private String function;
    private int n = 1;
    private int d, s, gbest, old, nf, k;
    private double[] w;
    private double[] c1 ;
    private double[] c2 ;
    private double r1, r2, e;
    private double[][] x;
    private double[][] v;
    private double maxSearchSpace;
    private double[] distance ;
    private double[][] p;
    private double[] xp;
    private double[] fp;
    private double[] fitnes;
    private double[] xmin;
    private double[] xmax;
    private double max_distance = 0;
    private int maxIterationCount = 10000;
    private int stopCause = 0;
    private double wo;
    private double r;
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

    public void setProperties(AdaptiveProperties properties) {
        n = properties.getN();
        maxIterationCount = properties.getK();
        stopCause = properties.getStopCause();
    }


    private void particleSwarmInitialization() {     /*Ініціалізація частинок рою*/
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
        for (int i = 0; i < s; i++) {      /*Оновлення поточного найкращого рішення частинки рою*/
            if (fitnes[i] < fp[i]) {
                fp[i] = fitnes[i];
                for (int j = 0; j < d; j++)
                    p[i][j] = x[i][j];
            }
        }
    }
    private void refreshCurrentSwarmBest(){
        for (int i = 0; i < s; i++) {  /*Оновлення найкращої частинки рою*/
            if (fp[i] < fp[gbest]) {
                old = gbest;
                gbest = i;
            }
        }
    }

    public AdaptiveAnswer solve() {
        AdaptiveAnswer answer = new AdaptiveAnswer();
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
            answer.setStopCause("Перевищено максимальну кількість ітерацій k = " + maxIterationCount);
        } else {
            answer.setStopCause("Різниця по модулю між старим кращим значенням і поточним менша за е = " + e + ".");
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
        max_distance = 0;
        w = new double [s];
        c1 = new double[s];
        c2 = new double[s];
        distance = new double[s];
        for(int i=0; i < s; i++){
            if(i != gbest) for(int j = 0; j < d; j++) distance[i] = distance[i] + sqr(x[gbest][j]-x[i][j]);
            distance[i] = Math.sqrt(distance[i]);
            if (distance[i] > max_distance) max_distance = distance[i];
        }

        for (int i = 0; i < s; i++) {
            wo = rand.nextDouble() / 2 + 0.5;
            w[i] = wo * (1 - distance[i] / max_distance);
            c1[i] = Math.sqrt(1 + w[i]) / 2;
            c2[i] = c1[i];
        }
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < d; j++) {
                r1 = rand.nextDouble();
                r2 = rand.nextDouble();
                v[i][j] = w[i] * v[i][j] + c1[i] * r1 * (p[i][j] - x[i][j]);
                if (i != gbest)
                    v[i][j] = v[i][j] + c2[i] * r2 * (p[gbest][j] - x[i][j]);
                r = rand.nextDouble()/2-0.25;
                x[i][j] = (1-r)*x[i][j] + v[i][j];
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
                if ((old != gbest) && (Math.abs(fp[old] - fp[gbest]) < e) || (k >= maxIterationCount)) {  /*Умова виходу з циклу*/
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
        if (x[i][j] < xmin[j]) {     /*Перевірка виходу частинки за межі простору пошуку*/
            x[i][j] = xmin[j];
            v[i][j] = 0;
        }
        if (x[i][j] > xmax[j]) {
            x[i][j] = xmax[j];
            v[i][j] = 0;
        }
    }

    private double sqr(double x){
        return x*x;
    }
}