package com.geiko.controller;
import com.geiko.model.AdaptiveAnswer;
import com.geiko.model.Problem;
import com.geiko.model.AdaptiveProperties;
import com.geiko.model.ProtocolRow;
import com.geiko.model.*;
import com.geiko.model.functions.*;
import com.geiko.service.impl.AdaptivePSOService;
import com.geiko.service.impl.FunctionFactory;
import com.geiko.view.ProtocolExcelView;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Андрей on 24.04.2017.
 */
@Controller
public class AdaptiveSolveController {

    @Autowired
    AdaptivePSOService AdaptivepsoService;
    @Autowired
    FunctionFactory functionFactory;
    AdaptiveAnswer answer;
    ArrayList<List<ProtocolRow>> protocols ;
    @Autowired
    AdaptiveProperties properties;

    @RequestMapping(value = {"/solve/adaptive"}, method = RequestMethod.GET)
    public String getSolve(Model model){
        model.addAttribute("problem",new Problem());
        model.addAttribute("properties",properties);
        return "/solvers/adaptivepso";
    }

    @RequestMapping(value = {"/solve/adaptive/{type}"}, method = RequestMethod.GET)
    public String getSolve(@PathVariable("type") String type,Model model)throws Exception{
        return "redirect:/solve/adaptive";
    }

    @RequestMapping(value = {"/solve/adaptive/protocol"}, method = RequestMethod.GET)
    public ModelAndView getProtocol(Model model){
        return new ModelAndView(new ProtocolExcelView(),"protocols",protocols);
    }
    @RequestMapping(value = {"/solve/properties/adaptive"}, method = RequestMethod.POST)
    public String setProperties(@Valid AdaptiveProperties properties, Model model){
        this.properties = properties;
        AdaptivepsoService.setProperties(properties);
        model.addAttribute("properties",properties);
        return "redirect:/solve/adaptive";
    }
    @RequestMapping(value = {"/solve/properties/adaptive"}, method = RequestMethod.GET)
    public String setDefaultProperties( Model model){
        this.properties = new AdaptiveProperties();
        AdaptivepsoService.setProperties(properties);
        model.addAttribute("properties",properties);
        return "redirect:/solve/adaptive";
    }

    @RequestMapping(value = {"/solve/adaptive/{type}"}, method = RequestMethod.POST)
    public String postSolve(@PathVariable("type") String type, @Valid Problem problem, Model model)throws Exception{
        if(type.equals("custom")){
            AdaptivepsoService.setF(new CustomFunction(problem.getFunction()));
            problem.setTestFunction(false);
        }else{
            problem.setFunction(type);
            AdaptivepsoService.setF(functionFactory.getFunction(type));
            problem.setTestFunction(true);
        }
        AdaptivepsoService.setE(problem.getE());
        AdaptivepsoService.setS(problem.getS());
        AdaptivepsoService.setD(problem.getD());
        AdaptivepsoService.setMaxSearchSpace(problem.getMaxSearchSpace());
        protocols = new ArrayList<>();
        AdaptiveAnswer answer = new AdaptiveAnswer();
        ArrayList<AdaptiveAnswer> answers = new ArrayList<>();
        for(int i = 0; i<AdaptivepsoService.getN();i++){
            AdaptiveAnswer ans = AdaptivepsoService.solve();
            List<ProtocolRow> protocol = ans.getProtocol();
            protocols.add(protocol);
            answers.add(ans);
        }
        double f=0;
        double k=0;
        double nf=0;
        double x[]= new double[AdaptivepsoService.getD()];
        for(AdaptiveAnswer a: answers){
            f+=a.getF();
            k+=a.getK();
            nf+=a.getNf();
            double currentX[]=a.getX();
            for (int i=0; i<currentX.length;i++) {
                x[i]+=currentX[i];
            }
            answer.setDataForCharts(a.getDataForCharts());
            answer.setStopCause(a.getStopCause());
            answer.setDataForX(a.getDataForX());
        }
        BigDecimal bdF=new BigDecimal(f/answers.size());
        answer.setF(bdF.setScale(15, BigDecimal.ROUND_HALF_EVEN).doubleValue());
        answer.setK((int)k/answers.size());
        answer.setNf((int)nf/answers.size());
        for (int i=0; i<x.length;i++) {
            BigDecimal bdX  = new BigDecimal(x[i]/answers.size());
            x[i]=bdX.setScale(15, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        }
        answer.setX(x);
        answer.setN(AdaptivepsoService.getN());
        model.addAttribute("answer", answer);
        model.addAttribute("answers", answers);
        model.addAttribute("properties",properties);
        return "/solvers/adaptiveanswer";
    }
}

