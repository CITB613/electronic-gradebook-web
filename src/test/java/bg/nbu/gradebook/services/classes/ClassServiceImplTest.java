package bg.nbu.gradebook.services.classes;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.assertj.core.util.Sets.set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Class;
import bg.nbu.gradebook.domain.entities.Subject;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import bg.nbu.gradebook.repositories.ClassRepository;
import bg.nbu.gradebook.services.subjects.SubjectService;
import bg.nbu.gradebook.services.users.UserService;

@ExtendWith(MockitoExtension.class)
class ClassServiceImplTest {
    private static final int SCHOOL_GRADE = 10;
    private static final String SCHOOL_GROUP = "schoolGroup";

    private static final long MISSING_STUDENT_ID = -1;
    private static final long STUDENT_ID = 1;

    private static final long CLASS_ID = 2;
    private static final long SUBJECT_ID = 3;

    @InjectMocks
    private ClassServiceImpl classService;

    @Mock
    private UserService userServiceMock;

    @Mock
    private SubjectService subjectServiceMock;

    @Mock
    private ClassRepository classRepositoryMock;

    @Mock
    private Mapper modelMapperMock;

    @Mock
    private User userMock;

    @Mock
    private Class classMock;

    @Mock
    private ClassServiceModel classServiceModelMock;

    @Mock
    private ClassBindingModel classBindingModelMock;

    @Mock
    private Set<User> studentsMock;

    @Mock
    private Set<Subject> subjectsMock;

    @Mock
    private Subject subjectMock;

    @BeforeEach
    void setUp() {
        lenient().when(userMock.getId())
                .thenReturn(STUDENT_ID);
        lenient().when(classMock.getId())
                .thenReturn(CLASS_ID);
        lenient().when(classMock.getStudents())
                .thenReturn(singleton(userMock));

        lenient().when(userServiceMock.findById(STUDENT_ID))
                .thenReturn(of(userMock));
        lenient().when(classRepositoryMock.findById(CLASS_ID))
                .thenReturn(of(classMock));
        lenient().when(subjectServiceMock.findById(SUBJECT_ID))
                .thenReturn(of(subjectMock));

        lenient().when(classRepositoryMock.save(classMock))
                .thenReturn(classMock);
        lenient().when(classRepositoryMock.findAll())
                .thenReturn(singletonList(classMock));

        lenient().when(modelMapperMock.map(classMock, ClassServiceModel.class))
                .thenReturn(classServiceModelMock);
        lenient().when(modelMapperMock.map(classBindingModelMock, Class.class))
                .thenReturn(classMock);

        lenient().when(classMock.getStudents())
                .thenReturn(studentsMock);
        lenient().when(studentsMock.add(any()))
                .thenReturn(true);
        lenient().when(studentsMock.remove(any()))
                .thenReturn(true);

        lenient().when(classMock.getSubjects())
                .thenReturn(subjectsMock);
        lenient().when(subjectsMock.add(any()))
                .thenReturn(true);
        lenient().when(subjectsMock.remove(any()))
                .thenReturn(true);

        lenient().when(classBindingModelMock.getGrade())
                .thenReturn(SCHOOL_GRADE);
        lenient().when(classBindingModelMock.getGroup())
                .thenReturn(SCHOOL_GROUP);
    }

    @Test
    void testEnrollStudentInClassThrowsExceptionWhenUserNotFound() {
        when(userServiceMock.findById(STUDENT_ID)).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> classService.enrollStudentInClass(STUDENT_ID, CLASS_ID));
    }

    @Test
    void testEnrollStudentInClassThrowsExceptionWhenClassNotFound() {
        when(classRepositoryMock.findById(CLASS_ID)).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> classService.enrollStudentInClass(STUDENT_ID, CLASS_ID));
    }

    @Test
    void testEnrollStudentInClass() {
        assertThat(classService.enrollStudentInClass(STUDENT_ID, CLASS_ID), equalTo(classServiceModelMock));

        verify(studentsMock).add(userMock);
        verify(classRepositoryMock).save(classMock);
    }

    @Test
    void testUnenrollStudentFromClassThrowsExceptionWhenUserNotFound() {
        when(userServiceMock.findById(STUDENT_ID)).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> classService.unenrollStudentFromClass(STUDENT_ID, CLASS_ID));
    }

    @Test
    void testUnenrollStudentFromClassThrowsExceptionWhenClassNotFound() {
        when(classRepositoryMock.findById(CLASS_ID)).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> classService.unenrollStudentFromClass(STUDENT_ID, CLASS_ID));
    }

    @Test
    void testUnenrollStudentFromClass() {
        classService.unenrollStudentFromClass(STUDENT_ID, CLASS_ID);

        verify(studentsMock).remove(userMock);
        verify(classRepositoryMock).save(classMock);
    }

    @Test
    void testCreate() {
        assertThat(classService.create(classBindingModelMock), equalTo(classServiceModelMock));

        verify(classRepositoryMock).save(classMock);
        verify(modelMapperMock).map(classMock, ClassServiceModel.class);
    }

    @Test
    void testAddSubjectThrowsExceptionWhenClassNotFound() {
        when(classRepositoryMock.findById(CLASS_ID)).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> classService.addSubject(CLASS_ID, SUBJECT_ID));
    }

    @Test
    void testAddSubjectThrowsExceptionWhenSubjectNotFound() {
        lenient().when(subjectServiceMock.findById(SUBJECT_ID))
                .thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> classService.addSubject(CLASS_ID, SUBJECT_ID));
    }

    @Test
    void testAddSubject() {
        classService.addSubject(CLASS_ID, SUBJECT_ID);

        verify(subjectsMock).add(subjectMock);
        verify(classRepositoryMock).save(classMock);
    }

    @Test
    void testRemoveSubjectThrowsExceptionWhenClassNotFound() {
        when(classRepositoryMock.findById(CLASS_ID)).thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> classService.removeSubject(CLASS_ID, SUBJECT_ID));
    }

    @Test
    void testRemoveSubjectThrowsExceptionWhenSubjectNotFound() {
        lenient().when(subjectServiceMock.findById(SUBJECT_ID))
                .thenReturn(empty());

        assertThrows(NoSuchElementException.class, () -> classService.removeSubject(CLASS_ID, SUBJECT_ID));
    }

    @Test
    void testRemoveSubject() {
        classService.removeSubject(CLASS_ID, SUBJECT_ID);

        verify(subjectsMock).remove(subjectMock);
        verify(classRepositoryMock).save(classMock);
    }

    @Test
    void testUpdateStudentClassThrowsExceptionWhenStudentNotFound() {
        assertThrows(NoSuchElementException.class,
                () -> classService.updateStudentClass(MISSING_STUDENT_ID, classBindingModelMock));
    }

    @Test
    void testUpdateStudentClassThrowsExceptionWhenClassNotFound() {
        when(classMock.getStudents()).thenReturn(set(userMock));
        when(classRepositoryMock.findByGradeAndGroup(anyInt(), anyString())).thenReturn(empty());

        assertThrows(NoSuchElementException.class,
                () -> classService.updateStudentClass(STUDENT_ID, classBindingModelMock));
    }

    @Test
    void testUpdateStudentClass() {
        when(classMock.getStudents()).thenReturn(set(userMock));
        when(classRepositoryMock.findByGradeAndGroup(anyInt(), anyString())).thenReturn(of(classMock));

        assertThat(classService.updateStudentClass(STUDENT_ID, classBindingModelMock), equalTo(classServiceModelMock));

        verify(classRepositoryMock, times(2)).save(classMock);
    }
}
