package bg.nbu.gradebook.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import bg.nbu.gradebook.services.classes.ClassService;

@ExtendWith(MockitoExtension.class)
class ClassControllerTest {

    private static final long CLASS_ID = 1;
    private static final long STUDENT_ID = 2;
    private static final long SUBJECT_ID = 0;

    @InjectMocks
    private ClassController classController;

    @Mock
    private ClassService classServiceMock;

    @Mock
    private ClassBindingModel classBindingModelMock;

    @Mock
    private ClassServiceModel classServiceModelMock;

    @BeforeEach
    void setUp() {
        lenient().when(classServiceMock.create(classBindingModelMock))
                .thenReturn(classServiceModelMock);

        lenient().when(classServiceMock.updateStudentClass(STUDENT_ID, classBindingModelMock))
                .thenReturn(classServiceModelMock);
    }

    @Test
    void testCreate() {
        assertThat(classController.create(classBindingModelMock), equalTo(classServiceModelMock));
    }

    @Test
    void testEnrollStudent() {
        classController.enrollStudent(CLASS_ID, STUDENT_ID);

        verify(classServiceMock).enrollStudentInClass(STUDENT_ID, CLASS_ID);
    }

    @Test
    void testUnenrollStudent() {
        classController.unenrollStudent(CLASS_ID, STUDENT_ID);

        verify(classServiceMock).unenrollStudentFromClass(STUDENT_ID, CLASS_ID);
    }

    @Test
    void testAddSubject() {
        classController.addSubject(CLASS_ID, SUBJECT_ID);

        verify(classServiceMock).addSubject(CLASS_ID, SUBJECT_ID);
    }

    @Test
    void testRemoveSubject() {
        classController.removeSubject(CLASS_ID, SUBJECT_ID);

        verify(classServiceMock).removeSubject(CLASS_ID, SUBJECT_ID);
    }

    @Test
    void testUpdateStudentClass() {
        assertThat(classController.updateStudentClass(STUDENT_ID, classBindingModelMock),
                equalTo(classServiceModelMock));

        verify(classServiceMock).updateStudentClass(STUDENT_ID, classBindingModelMock);
    }
}
