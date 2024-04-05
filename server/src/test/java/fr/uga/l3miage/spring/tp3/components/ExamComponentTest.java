package fr.uga.l3miage.spring.tp3.components;


import fr.uga.l3miage.spring.tp3.repositories.CandidateEvaluationGridRepository;
import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
import fr.uga.l3miage.spring.tp3.repositories.SkillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

public class ExamComponentTest {
    @Autowired
    @MockBean
    private ExamComponent examComponent;

    @MockBean
    private SkillRepository skillRepository;

    @MockBean
    private ExamRepository examRepository;

    @Test

}
