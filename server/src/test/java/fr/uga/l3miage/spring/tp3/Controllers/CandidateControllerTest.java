package fr.uga.l3miage.spring.tp3.Controllers;

import fr.uga.l3miage.spring.tp3.controller.CandidateController;
import fr.uga.l3miage.spring.tp3.exceptions.CandidatNotFoundResponse;
import fr.uga.l3miage.spring.tp3.exceptions.rest.CandidateNotFoundRestException;
import fr.uga.l3miage.spring.tp3.models.CandidateEntity;
import fr.uga.l3miage.spring.tp3.models.CandidateEvaluationGridEntity;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.repositories.CandidateRepository;
import fr.uga.l3miage.spring.tp3.services.CandidateService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidateController.class)
class CandidateControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CandidateRepository candidateRepository;

    @Test
    void getCandidateAverageFound() {

        // Given
        HttpHeaders headers = new HttpHeaders();

        CandidateEntity candidate = CandidateEntity.builder()
                .id(1L)
                .email("ouerghi@gmail.com")
                .candidateEvaluationGridEntities(new HashSet<>())
                .build();

        CandidateEvaluationGridEntity grid1 = CandidateEvaluationGridEntity.builder()
                .grade(5)
                .examEntity(ExamEntity.builder().weight(1).build())
                .build();

        CandidateEvaluationGridEntity grid2 = CandidateEvaluationGridEntity.builder()
                .grade(10)
                .examEntity(ExamEntity.builder().weight(1).build())
                .build();

        candidate.getCandidateEvaluationGridEntities().add(grid1);
        candidate.getCandidateEvaluationGridEntities().add(grid2);

        candidateRepository.save(candidate);

        // When
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/candidates/{candidateId}/average", HttpMethod.GET, null, String.class, 1L);
        // Then
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    void getCandidateAverageNotFound() {

        // Given
        final Map<String, Object> urlParams = new HashMap<>();
        urlParams.put("CandidateID", "ID inexistent");

        CandidatNotFoundResponse expectedResponse = CandidatNotFoundResponse.builder()
                .uri(null)
                .errorMessage(null)
                .candidateId(null)
                .build();

        // When
        ResponseEntity<CandidatNotFoundResponse> response = testRestTemplate.exchange("/api/candidates/{candidateId}/average", HttpMethod.GET, null, CandidatNotFoundResponse.class, urlParams);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
    }



}
