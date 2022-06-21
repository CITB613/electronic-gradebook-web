package bg.nbu.gradebook.controllers;

import static java.util.Collections.singletonList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Class;
import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.models.bindings.SchoolBindingModel;
import bg.nbu.gradebook.services.schools.SchoolService;

@ExtendWith(MockitoExtension.class)
class SchoolControllerTest {
    private static final long SCHOOL_ID = 1;
    private static final long USER_ID = 2;

    @InjectMocks
    private SchoolController schoolController;

    @Mock
    private SchoolService schoolServiceMock;

    @Mock
    private Mapper mapperMock;

    @Mock
    private SchoolBindingModel schoolBindingModelMock;

    @Mock
    private School schoolMock;

    @Mock
    private Class classMock;

    @BeforeEach
    void setUp() {
        lenient().when(schoolServiceMock.registerSchool(schoolBindingModelMock))
                .thenReturn(schoolMock);
        lenient().when(mapperMock.map(schoolMock, SchoolBindingModel.class))
                .thenReturn(schoolBindingModelMock);
        lenient().when(schoolServiceMock.findAll())
                .thenReturn(singletonList(schoolMock));
        lenient().when(mapperMock.mapCollection(anyList(), eq(SchoolBindingModel.class)))
                .thenReturn(singletonList(schoolBindingModelMock));
        lenient().when(schoolMock.getClasses())
                .thenReturn(singletonList(classMock));
    }

    @Test
    void testRegisterSchool() {
        assertThat(schoolController.registerSchool(schoolBindingModelMock), equalTo(schoolBindingModelMock));

        verify(schoolServiceMock).registerSchool(schoolBindingModelMock);
        verify(mapperMock).map(schoolMock, SchoolBindingModel.class);
    }

    @Test
    void testSetPrincipal() {
        schoolController.setPrincipal(SCHOOL_ID, USER_ID);

        verify(schoolServiceMock).setPrincipal(SCHOOL_ID, USER_ID);
    }

    @Test
    void testGetAllSchools() {
        assertThat(schoolController.getAllSchools(), equalTo(singletonList(schoolBindingModelMock)));
    }

    @Test
    void testGetAllClassesBySchoolThrowsExceptionWhenSchoolNotFound() {
        assertThrows(NoSuchElementException.class, () -> schoolController.getAllClassesBySchool(SCHOOL_ID));
    }

    @Test
    void testGetAllClassesBySchool() {
        when(schoolMock.getId()).thenReturn(SCHOOL_ID);

        assertThat(schoolController.getAllClassesBySchool(SCHOOL_ID), equalTo(singletonList(classMock)));
    }

    @Test
    void testUpdateSchool() {
        schoolController.updateSchool(SCHOOL_ID, schoolBindingModelMock);

        verify(schoolServiceMock).update(SCHOOL_ID, schoolBindingModelMock);
    }
}
