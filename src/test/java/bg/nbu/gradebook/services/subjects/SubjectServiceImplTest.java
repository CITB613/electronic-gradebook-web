package bg.nbu.gradebook.services.subjects;

import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Subject;
import bg.nbu.gradebook.domain.models.service.SubjectServiceModel;
import bg.nbu.gradebook.repositories.SubjectRepository;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {
    private static final long SUBJECT_ID = 1;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Mock
    private SubjectRepository subjectRepositoryMock;

    @Mock
    private Mapper mapperMock;

    @Mock
    private SubjectServiceModel subjectServiceModelMock;

    @Mock
    private Subject subjectMock;

    @BeforeEach
    void setUp() {
        lenient().when(subjectRepositoryMock.save(subjectMock))
                .thenReturn(subjectMock);
        lenient().when(subjectRepositoryMock.findById(anyLong()))
                .thenReturn(of(subjectMock));
        lenient().when(subjectRepositoryMock.findAll())
                .thenReturn(singletonList(subjectMock));

        lenient().when(mapperMock.map(subjectServiceModelMock, Subject.class))
                .thenReturn(subjectMock);
        lenient().when(mapperMock.map(subjectMock, SubjectServiceModel.class))
                .thenReturn(subjectServiceModelMock);
        lenient().when(mapperMock.mapCollection(anyList(), eq(SubjectServiceModel.class)))
                .thenReturn(singletonList(subjectServiceModelMock));
    }

    @Test
    void testFindAll() {
        assertThat(subjectService.findAll(), equalTo(singletonList(subjectServiceModelMock)));

        verify(mapperMock).mapCollection(singletonList(subjectMock), SubjectServiceModel.class);
    }

    @Test
    void testCreateSubject() {
        assertThat(subjectService.createSubject(subjectServiceModelMock), equalTo(subjectServiceModelMock));

        verify(subjectRepositoryMock).save(subjectMock);
        verify(mapperMock).map(subjectServiceModelMock, Subject.class);
        verify(mapperMock).map(subjectMock, SubjectServiceModel.class);
    }

    @Test
    void testFindById() {
        assertThat(subjectService.findById(SUBJECT_ID), equalTo(of(subjectMock)));
    }
}
