package fr.uga.l3miage.spring.tp3.component;

import fr.uga.l3miage.spring.tp3.exceptions.technical.ExamNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import fr.uga.l3miage.spring.tp3.components.ExamComponent;
import fr.uga.l3miage.spring.tp3.models.ExamEntity;
import fr.uga.l3miage.spring.tp3.models.SkillEntity;
import fr.uga.l3miage.spring.tp3.repositories.ExamRepository;
import fr.uga.l3miage.spring.tp3.repositories.SkillRepository;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ExamComponentTest {

    @Mock
    private SkillRepository skillRepository;

    @Mock
    private ExamRepository examRepository;

    @InjectMocks
    private ExamComponent examComponent;

    @Test
    void getAllCardioExamFound() throws Exception{
        // Given
        SkillEntity cardioSkill = new SkillEntity();
        when(skillRepository.findByNameLike("cardio")).thenReturn(Optional.of(cardioSkill));
        when(examRepository.findAllBySkillEntitiesContaining(cardioSkill)).thenReturn(Set.of());

        // When
        Set<ExamEntity> result = examComponent.getAllCardioExam();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }

    @Test
    void getAllByIdFound() throws Exception{
        // Given
        Set<Long> examIds = Set.of(1L, 2L);
        Set<ExamEntity> expectedExams = new HashSet<>();
        expectedExams.add(new ExamEntity());
        expectedExams.add(new ExamEntity());

        when(examRepository.findAllById(examIds)).thenReturn(List.copyOf(expectedExams));

        // When
        Set<ExamEntity> actualExams = examComponent.getAllById(examIds);

        // Then
        assertThat(actualExams).isNotEmpty();
        assertThat(actualExams).hasSameSizeAs(expectedExams);
        assertThat(actualExams).containsAll(expectedExams);
    }

    @Test
    void getAllCardioExamNotFound() {
        // Given
        when(skillRepository.findByNameLike("cardio")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> {
            examComponent.getAllCardioExam();
        });
    }

    @Test
    void getAllByIdNotFound() {
        // Given
        Set<Long> examIds = Set.of(1L, 2L);
        Set<ExamEntity> foundExams = Set.of(new ExamEntity());
        when(examRepository.findAllById(examIds)).thenReturn(List.copyOf(foundExams));

        // When & Then
        assertThrows(ExamNotFoundException.class, () -> {
            examComponent.getAllById(examIds);
        });
    }

}
