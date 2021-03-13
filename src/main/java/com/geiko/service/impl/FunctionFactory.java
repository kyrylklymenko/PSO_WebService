package com.geiko.service.impl;

import com.geiko.model.functions.*;
import org.springframework.stereotype.Service;
import java.util.HashMap;

/**
 * Created by Андрей on 18.05.2017.
 */
@Service
public class FunctionFactory {
    private HashMap<String,Function> functions = new HashMap<>();

    public FunctionFactory() {
        functions.put("elliptical",new EllipticalFunction());
        functions.put("rosenbroke",new RosenbrokeFunction());
        functions.put("spherical",new SphericalFunction());
        functions.put("schwefel2",new Schwefel2Function());
        functions.put("weierstrass",new WeierstrassFunction());
        functions.put("aqley",new AqleyFunction());
        functions.put("alpine",new AlpineFunction());
        functions.put("schwefel",new SchwefelFunction());
        functions.put("grivonk",new GrivonkFunction());
        functions.put("rastrigin",new RastriginFunction());
        functions.put("complex1",new RastriginFunction());
        functions.put("complex2",new RastriginFunction());
        functions.put("complex3",new RastriginFunction());
    }
    public Function getFunction(String type){
        return functions.get(type);
    }
}
