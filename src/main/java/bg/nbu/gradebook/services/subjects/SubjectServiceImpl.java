package bg.nbu.gradebook.services.subjects;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Subject;
import bg.nbu.gradebook.domain.models.service.SubjectServiceModel;
import bg.nbu.gradebook.repositories.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final Mapper mapper;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository, Mapper mapper) {
        this.subjectRepository = subjectRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SubjectServiceModel> findAll() {
        return mapper.mapCollection(subjectRepository.findAll(), SubjectServiceModel.class);
    }

    @Override
    public SubjectServiceModel createSubject(SubjectServiceModel subjectServiceModel) {
        final Subject subject = subjectRepository.save(mapper.map(subjectServiceModel, Subject.class));

        return mapper.map(subject, SubjectServiceModel.class);
    }

    @Override
    public Optional<Subject> findById(long subjectId) {
        return subjectRepository.findById(subjectId);
    }

    @Override
    public void deleteById(long id) {
        subjectRepository.deleteById(id);
    }
}
