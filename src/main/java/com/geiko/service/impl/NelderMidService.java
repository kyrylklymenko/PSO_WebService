package com.geiko.service.impl;

import com.geiko.model.Answer;
import com.geiko.model.functions.Function;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * Created by Андрей on 09.06.2017.
 */
@Service
public class NelderMidService {
    private double e;
    private double alpha,beta,gamma,lambda,sigma,delta;
    private int n;
    private double X[][];
    private double f[];
    private double XB[];
    private double XG[];
    private double XW[];
    private double fb,fg,fw;
    private double XM[];
    private double XR[];
    private double XL[];
    private double XU[];
    private double XV[];
    private double XC[];
    private double fm,fr,fl,fu,fv,fc;
    private double y[];
    private int k,nf,b,g,w;
    private Function fun;

    public Function getFun() {
        return fun;
    }

    public void setFun(Function fun) {
        this.fun = fun;
    }

    private void init(Function function, double x[], double e){
        this.n=x.length;
        X=new double[n+1][n];
        f=new double[n+1];
        XB=new double[n];
        XG=new double[n];
        XW=new double[n];
        XM=new double[n];
        XR=new double[n];
        XL=new double[n];
        XU=new double[n];
        XV=new double[n];
        XC=new double[n];
        y=new double[n];
        lambda = 1;
        alpha=1;
        beta =2;
        gamma=0.5;
        nf=0;
        k=0;
        fun = function;
        fun.setD(n);
        this.e=e;
        for(int j = 0;j<y.length;j++){
            X[0][j]=new Double(x[j]);
            y[j]=X[0][j];
        }
        f[0]=fun.getResult(y);
    }

    private void sort(){
        double max = f[0];
        int nx = 0;
        double min = f[0];
        int nn = 0;
        for(int i =0; i<f.length;i++){
            if (f[i]>=max){
                max=f[i];
                nx=i;
            }
            if(f[i]<min){
                min=f[i];
                nn=i;
            }
        }
        b=nn;
        w=nx;
        if(nx!=0){
            max=f[0];
            nx=0;
        }else{
            max=f[1];
            nx=1;
        }
        for(int i =0; i<f.length;i++){
            if(i!=w){
                if(f[i]>max){
                    max=f[i];
                    nx=i;
                }
            }
        }
        g=nx;
        for(int i =0;i<y.length;i++){
            y[i]=X[b][i];
        }
        fb=fun.getResult(y);
        nf++;
        for(int i =0;i<y.length;i++){
            y[i]=X[g][i];
        }
        fg=fun.getResult(y);
        nf++;
        for(int i =0;i<y.length;i++){
            y[i]=X[w][i];
        }
        fw=fun.getResult(y);
        nf++;
    }

    private void centr(){
        double s;
        for(int j = 0;j<XM.length;j++){
            s=0;
            for(int i =0;i<XM.length+1;i++){
                if(i!=w){
                    s+=X[i][j];
                }
            }
            XM[j]=s/(double)XM.length;
            fm=fun.getResult(XM);
            nf++;
        }
    }
    private void chainge(double z[],double fz){
        for (int  i =0; i<z.length;i++){
            X[w][i]=z[i];
        }
        f[w]=fz;
    }

    private void press(){
        for(int j = 0; j<XU.length;j++){
            XU[j]=gamma*XM[j]+(1-gamma)*X[w][j];
            XV[j]=gamma*XM[j]+(1-gamma)*XR[j];
        }
        fu=fun.getResult(XU);
        nf++;
        fv=fun.getResult(XV);
        nf++;
        if(fu<fv){
            XC=XU;
            fc=fu;
        }else{
            XC=XV;
            fc=fv;
        }
    }
    private void concentrate(){
        for(int i=0; i < f.length;i++){
            if(i!=b){
                for(int j=0;j<y.length;j++){
                    X[i][j]=(X[i][j]+X[b][j])/2.0;
                    y[j]=X[i][j];
                }
                f[i]=fun.getResult(y);
                nf++;
            }
        }
    }
    private void deviation(){
        double fd = 0;
        for(int i =0;i<f.length;i++){
            fd+=f[i];
        }
        fd/=(double)(n+1);
        sigma = 0;
        for(int i =0; i <f.length;i++){
            sigma+= Math.pow((f[i]-fd),2);
        }
        sigma=Math.sqrt( sigma/(double)(n+1));
    }
    private void size(){
        double norma;
        delta=0;
        for(int i =0; i<X.length;i++){
            norma=0;
            for(int j =0;j<X[i].length;j++){
                norma+=Math.pow((X[i][j]-X[b][j]),2);
            }
            norma=Math.sqrt(norma);
            if (norma>=delta){
                delta=norma;
            }
        }
    }
    public Answer solve(double x[],Function function, double e){
        init(function,x,e);
        nf++;
        createSimplex();
        boolean end = false;
        do{
            k++;
            sort();
            centr();
            reflection();
            nf++;
            checkReflection();
            deviation();
            size();

        }while (!isEndOfExecution());
        Answer answer = new Answer();
        answer.setX(X[b]);
        answer.setF(fb);
        answer.setK(k);
        answer.setNf(nf);
        return answer;
    }

    private void createSimplex() {
        for(int i =1;i<f.length;i++){
            for(int j = 0; j<y.length;j++){
                X[i][j]=X[0][j];
                y[j]=X[i][j];
            }
            X[i][i-1]=X[i][i-1]+lambda;
            y[i-1]=X[i][i-1];
            f[i]=fun.getResult(y);
            nf++;
        }
    }

    private void checkReflection() {
        if(fr<fg){
            if(fb<fr){
                chainge(XR,fr);
            }else{
                for(int j =0;j< XM.length;j++){
                    XL[j]=XM[j]+beta*(XM[j]-X[w][j]);
                }
                fl=fun.getResult(XL);
                nf++;
                if(fl<fb){
                    chainge(XL,fl);
                }else{
                    chainge(XR,fr);
                }
            }
        }else{
            if(fr<fw){
                chainge(XR,fr);
            }else{
                press();
                if(fc<fw){
                    chainge(XC,fc);
                }else{
                    concentrate();
                }
            }
        }
    }

    private void reflection() {
        for(int j=0;j<XR.length;j++){
            XR[j]=XM[j]+alpha*(XM[j]-X[w][j]);
        }
        fr=fun.getResult(XR);
    }

    public boolean isEndOfExecution() {
        if(sigma<e)
            if(delta<e)
                return true;
        return false;
    }
}
