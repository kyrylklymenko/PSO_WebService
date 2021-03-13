package com.geiko.controller;

import com.geiko.model.Answer;
import com.geiko.model.Problem;
import com.geiko.model.Properties;
import com.geiko.model.ProtocolRow;
import com.geiko.model.functions.*;
import com.geiko.service.impl.CirclePSOService;
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
public class CircleSolveController {

    @Autowired
    CirclePSOService psoService;
    @Autowired
    FunctionFactory functionFactory;
    Answer answer;
    ArrayList<List<ProtocolRow>> protocols ;
    @Autowired
    Properties properties;

    @RequestMapping(value = {"/solve/circle"}, method = RequestMethod.GET)
    public String getSolve(Model model){
        model.addAttribute("problem",new Problem());
        model.addAttribute("properties",properties);
        return "/solvers/circlepso";
    }

    @RequestMapping(value = {"/solve/circle/{type}"}, method = RequestMethod.GET)
    public String getSolve(@PathVariable("type") String type,Model model)throws Exception{
        return "redirect:/solve/circle";
    }

    @RequestMapping(value = {"/solve/circle/protocol"}, method = RequestMethod.GET)
    public ModelAndView getProtocol(Model model){
        return new ModelAndView(new ProtocolExcelView(),"protocols",protocols);
    }

    @RequestMapping(value = {"/solve/properties/circle"}, method = RequestMethod.POST)
    public String setProperties(@Valid Properties properties, Model model){
        this.properties = properties;
        psoService.setProperties(properties);
        model.addAttribute("properties",properties);
        return "redirect:/solve/circle";
    }
    @RequestMapping(value = {"/solve/properties/circle"}, method = RequestMethod.GET)
    public String setDefaultProperties( Model model){
        this.properties = new Properties();
        psoService.setProperties(properties);
        model.addAttribute("properties",properties);
        return "redirect:/solve/circle";
    }

    @RequestMapping(value = {"/solve/circle/{type}"}, method = RequestMethod.POST)
    public String postSolve(@PathVariable("type") String type, @Valid Problem problem, Model model)throws Exception{
        if(type.equals("custom")){
            psoService.setF(new CustomFunction(problem.getFunction()));
            problem.setTestFunction(false);
        }else{
            problem.setFunction(type);
            psoService.setF(functionFactory.getFunction(type));
            problem.setTestFunction(true);
        }
        psoService.setE(problem.getE());
        psoService.setS(problem.getS());
        psoService.setD(problem.getD());
        psoService.setMaxSearchSpace(problem.getMaxSearchSpace());
        protocols = new ArrayList<>();
        answer = new Answer();
        ArrayList<Answer> answers = new ArrayList<>();
        for(int i = 0; i<psoService.getN();i++){
            Answer ans = psoService.solve();
            List<ProtocolRow> protocol = ans.getProtocol();
            protocols.add(protocol);
            answers.add(ans);
        }
        double f=0;
        double k=0;
        double nf=0;
        double x[]= new double[psoService.getD()];
        for(Answer a: answers){
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
        answer.setN(psoService.getN());
        model.addAttribute("answer", answer);
        model.addAttribute("answers", answers);
        model.addAttribute("properties",properties);
        return "/solvers/circleanswer";
    }
}