package fr.uga.l3miage.spring.tp3.controller;

import fr.uga.l3miage.spring.tp3.endpoints.CandidateEndpoints;
import fr.uga.l3miage.spring.tp3.services.CandidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class CandidateController implements CandidateEndpoints {
    private final CandidateService candidateService;

    @Override
    public Double getCandidateAverage(Long candidateId) {
        return candidateService.getCandidateAverage(candidateId);
    }

    @Override
    public Boolean addCandidatesToTestCenter(Long testCenterId, Set<Long> candidateIds) {
        try {
            return candidateService.addCandidatesToTestCenter(testCenterId, candidateIds);
        } catch (Exception e) {
            // Log l'exception
            throw e;
        }
    }

}
