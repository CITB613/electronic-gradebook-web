package bg.nbu.gradebook.controllers;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.nbu.gradebook.domain.models.service.SubjectServiceModel;
import bg.nbu.gradebook.services.subjects.SubjectService;

@ExtendWith(MockitoExtension.class)
class SubjectControllerTest {
    private static final int ID = 1;

    @InjectMocks
    private SubjectController subjectController;

    @Mock
    private SubjectService subjectServiceMock;

    private SubjectServiceModel subjectServiceModelMock;

    @Test
    void testCreateSubject() {
        when(subjectServiceMock.createSubject(subjectServiceModelMock)).thenReturn(subjectServiceModelMock);

        assertThat(subjectController.createSubject(subjectServiceModelMock), equalTo(subjectServiceModelMock));
    }

    @Test
    void testGetAllSubjects() {
        when(subjectServiceMock.findAll()).thenReturn(singletonList(subjectServiceModelMock));

        assertThat(subjectController.getAllSubjects(), contains(subjectServiceModelMock));
    }

    @Test
    void testDelete() {
        subjectController.delete(ID);

        verify(subjectServiceMock).deleteById(ID);
    }
}
