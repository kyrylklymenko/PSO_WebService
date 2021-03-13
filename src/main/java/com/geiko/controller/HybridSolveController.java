package com.geiko.controller;

import com.geiko.model.*;
import com.geiko.model.functions.CustomFunction;
import com.geiko.service.impl.FunctionFactory;
import com.geiko.service.impl.HybridPSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 09.06.2017.
 */
@Controller
public class HybridSolveController {

    @Autowired
    HybridPSOService hybridPSOService;
    @Autowired
    FunctionFactory functionFactory;
    @Autowired
    Properties properties;

    @RequestMapping(value = {"/solve/hybrid"}, method = RequestMethod.GET)
    public String getSolve(Model model){
        model.addAttribute("problem",new Problem());
        model.addAttribute("properties",properties);
        return "/solvers/hybridpso";
    }


    @RequestMapping(value = {"/solve/hybrid/{type}"}, method = RequestMethod.POST)
    public String postSolve(@PathVariable("type") String type, @Valid Problem problem, Model model)throws Exception{
        if(type.equals("custom")){
            problem.setF(new CustomFunction(problem.getFunction()));
            problem.setTestFunction(false);
        }else{
            problem.setFunction(type);
            problem.setF(functionFactory.getFunction(type));
            problem.setTestFunction(true);
        }
        HybridAnswer answer=hybridPSOService.solve(problem);
        answer.setN(properties.getN());
        model.addAttribute("answer",answer);
        model.addAttribute("properties",properties);
        return "/solvers/hybridanswer";
    }
    @RequestMapping(value = {"/solve/properties/hybrid"}, method = RequestMethod.POST)
    public String setProperties(@Valid Properties properties, Model model){
        this.properties = properties;
        hybridPSOService.setProperties(properties);
        model.addAttribute("properties",properties);
        return "redirect:/solve/hybrid";
    }
    @RequestMapping(value = {"/solve/properties/hybrid"}, method = RequestMethod.GET)
    public String setDefaultProperties( Model model){
        this.properties = new Properties();
        hybridPSOService.setProperties(properties);
        model.addAttribute("properties",properties);
        return "redirect:/solve/hybrid";
    }
}
