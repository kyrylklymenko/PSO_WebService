package com.geiko.service.impl;

import com.geiko.model.Answer;
import com.geiko.model.HybridAnswer;
import com.geiko.model.Problem;
import com.geiko.model.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Андрей on 09.06.2017.
 */
@Service
public class HybridPSOService {
    @Autowired
    CanonicalPSOService psoService;
    @Autowired
    NelderMidService nelderMidService;

    public CanonicalPSOService getPsoService() {
        return psoService;
    }

    public void setPsoService(CanonicalPSOService psoService) {
        this.psoService = psoService;
    }

    public NelderMidService getNelderMidService() {
        return nelderMidService;
    }

    public void setNelderMidService(NelderMidService nelderMidService) {
        this.nelderMidService = nelderMidService;
    }

    public HybridAnswer solve(Problem problem) {
        psoService.setF(problem.getF());
        psoService.setE(problem.getE());
        psoService.setS(problem.getS());
        psoService.setD(problem.getD());
        psoService.setMaxSearchSpace(problem.getMaxSearchSpace());
        Answer PSOanswer = psoService.solve();
        HybridAnswer answer = new HybridAnswer(PSOanswer);
        Answer neldMidAnswer = nelderMidService.solve(PSOanswer.getX(), problem.getF(), problem.getE());
        answer.setNmX(neldMidAnswer.getX());
        answer.setNmF(neldMidAnswer.getF());
        answer.setNmK(neldMidAnswer.getK());
        answer.setNmNf(neldMidAnswer.getNf());
        return answer;
    }
    public void setProperties(Properties properties){
        psoService.setProperties(properties);
    }
}
