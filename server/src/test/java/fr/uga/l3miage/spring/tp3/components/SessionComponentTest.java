package fr.uga.l3miage.spring.tp3.components;

import fr.uga.l3miage.spring.tp3.components.SessionComponent;
import fr.uga.l3miage.spring.tp3.models.EcosSessionEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationEntity;
import fr.uga.l3miage.spring.tp3.models.EcosSessionProgrammationStepEntity;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionProgrammationStepRepository;
import fr.uga.l3miage.spring.tp3.repositories.EcosSessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class SessionComponentTest {

    @Mock
    private EcosSessionRepository ecosSessionRepository;

    @Mock
    private EcosSessionProgrammationRepository ecosSessionProgrammationRepository;

    @Mock
    private EcosSessionProgrammationStepRepository ecosSessionProgrammationStepRepository;

    @InjectMocks
    private SessionComponent sessionComponent;

    @Test
    void createSessionTest() {
        // Given
        EcosSessionProgrammationStepEntity stepEntity = new EcosSessionProgrammationStepEntity();

        EcosSessionProgrammationEntity programmationEntity = new EcosSessionProgrammationEntity();
        programmationEntity.setEcosSessionProgrammationStepEntities(Set.of(stepEntity));

        EcosSessionEntity session = new EcosSessionEntity();
        session.setEcosSessionProgrammationEntity(programmationEntity);

        when(ecosSessionRepository.save(any(EcosSessionEntity.class))).thenReturn(session);

        // When
        EcosSessionEntity savedSession = sessionComponent.createSession(session);

        // Then
        assertThat(savedSession.getEcosSessionProgrammationEntity().getEcosSessionProgrammationStepEntities())
                .isNotNull()
                .containsExactlyInAnyOrder(stepEntity);
    }
}
