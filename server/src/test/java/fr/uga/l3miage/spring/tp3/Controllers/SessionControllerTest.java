package fr.uga.l3miage.spring.tp3.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.uga.l3miage.spring.tp3.controller.SessionController;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import fr.uga.l3miage.spring.tp3.request.SessionCreationRequest;
import fr.uga.l3miage.spring.tp3.request.SessionProgrammationCreationRequest;
import fr.uga.l3miage.spring.tp3.responses.SessionResponse;
import fr.uga.l3miage.spring.tp3.services.SessionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@AutoConfigureTestDatabase

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect")
public class SessionControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private SessionService sessionService;
    @Autowired
    private EcosSessionRepository sessionRepository;
    @AfterEach
    public void clear(){
        sessionRepository.deleteAll();
    }
    @Test
    void createSessionSuccess(){
        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .examsId(new HashSet<>())
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        ResponseEntity<SessionResponse> responseEntity = testRestTemplate.postForEntity("/api/sessions/create", request, SessionResponse.class);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void createSessionWithInvalidExamId() {

        Set<Long> invalidExamIds = new HashSet<>();
        invalidExamIds.add(-5L);
        SessionCreationRequest request = SessionCreationRequest.builder()
                .name("test")
                .startDate(LocalDateTime.MIN)
                .endDate(LocalDateTime.MAX)
                .examsId(invalidExamIds)
                .ecosSessionProgrammation(SessionProgrammationCreationRequest.builder().steps(new HashSet<>()).build())
                .build();

        // Envoyer la requête et obtenir la réponse
        ResponseEntity<String> responseEntity = testRestTemplate.postForEntity("/api/sessions/create", request, String.class);

        // Vérifier que la réponse est bien un statut 400 (BAD_REQUEST)
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}