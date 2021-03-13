package com.geiko.controller;

import com.geiko.model.*;
import com.geiko.model.functions.*;
import com.geiko.service.impl.AdaptivePSOService;
import com.geiko.service.impl.CanonicalPSOService;
import com.geiko.service.impl.FunctionFactory;
import com.geiko.service.impl.HybridPSOService;
import com.geiko.model.Portfolio2Answer;
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
public class Portfolio2Controller {

    @Autowired
    CanonicalPSOService CanpsoService;
    AdaptivePSOService AdapsoService;
    HybridPSOService HybpsoService;
    Answer answer;
    ArrayList<List<ProtocolRow>> protocols ;

    @RequestMapping(value = {"/solve/portfolio2"}, method = RequestMethod.GET)
    public String getSolve(Model model){
        model.addAttribute("portfolioproblem",new Portfolio2Problem());
        return "/solvers/portfolio2";
    }



    @RequestMapping(value = {"/solve/portfolio2/"}, method = RequestMethod.POST)
    public String postSolve(@PathVariable("type") String type, @Valid Portfolio2Problem problem, Model model)throws Exception{

        CanpsoService.setE(problem.getE());
        CanpsoService.setS(problem.getS());
        protocols = new ArrayList<>();
        answer = new Answer();
        ArrayList<Answer> answers = new ArrayList<>();
        for(int i = 0; i<CanpsoService.getN();i++){
            Answer ans = CanpsoService.solve();
            answers.add(ans);
        }
        double f=0;
        double k=0;
        double nf=0;
        double x[]= new double[CanpsoService.getD()];
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
        model.addAttribute("answer", answer);
        model.addAttribute("answers", answers);
        return "/solvers/answer";
    }
}