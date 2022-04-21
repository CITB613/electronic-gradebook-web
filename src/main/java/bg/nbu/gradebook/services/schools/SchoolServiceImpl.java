package bg.nbu.gradebook.services.schools;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.models.service.SchoolServiceModel;
import bg.nbu.gradebook.repositories.SchoolRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    private static final String SCHOOL_ALREADY_REGISTED_ERROR_TEMPLATE = "School {} located at {} is already registed";

    private final SchoolRepository schoolRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, ModelMapper modelMapper) {
        this.schoolRepository = schoolRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void registerSchool(SchoolServiceModel schoolServiceModel) {
        schoolRepository.findByName(schoolServiceModel.getName())
                .ifPresentOrElse(
                        school -> log.error(SCHOOL_ALREADY_REGISTED_ERROR_TEMPLATE, school.getName(),
                                school.getAddress()),
                        () -> schoolRepository.saveAndFlush(modelMapper.map(schoolServiceModel, School.class)));
    }
}