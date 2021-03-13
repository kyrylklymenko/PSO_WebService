package com.geiko.model.functions;

import com.geiko.service.impl.FunctionParser;

/**
 * Created by Андрей on 18.05.2017.
 */

public class CustomFunction extends Function {
    FunctionParser f = new FunctionParser();

    public CustomFunction(String function) {
        f.setFunction(function);
    }

    @Override
    public double getResult(double[] x) {
        f.setD(d);
        return f.getValueFromStringFunction(x);
    }
}
