package com.geiko.service.impl;

import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * Created by Андрей on 09.05.2017.
 */
@Service
public class FunctionParser {
    private int d;
    private String function;
    private String [] functions = {"sin","cos","sqrt","pow","PI","E","abs","exp","log"};
    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("JavaScript");

    public void setD(int d) {
        engine.put("d",d);
        this.d = d;
    }

    public void setFunction(String function) {
        for (String s:functions) {
            function=function.replaceAll(s,"Math."+s);
        }
        try{
            engine.eval("function sum(from,to,func){" +
                    "var s = 0;" +
                    "for (i = from-1; i < to; i++)" +
                    "s+=eval(func);" +
                    "return s};");
            engine.eval("function comp(from,to,func){" +
                    "var s = 1;" +
                    "for (i = from-1; i < to; i++)" +
                    "s*=eval(func);" +
                    "return s};");
        }catch (Exception e){}
        this.function = function;
    }


    public double getValueFromStringFunction(double x[]) {
        engine.put("x",x);
        try{
            return (double)engine.eval(function);
        }catch (Exception e){
            return 0;
        }
    }
}
