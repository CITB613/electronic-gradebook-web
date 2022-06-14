package bg.nbu.gradebook.services.schools;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.SchoolBindingModel;
import bg.nbu.gradebook.repositories.SchoolRepository;
import bg.nbu.gradebook.services.users.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    private static final String SCHOOL_ALREADY_REGISTED_ERROR_TEMPLATE = "School {} located at {} is already registed";

    private final SchoolRepository schoolRepository;
    private final UserService userService;
    private final Mapper modelMapper;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, UserService userService, Mapper modelMapper) {
        this.schoolRepository = schoolRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public School registerSchool(SchoolBindingModel schoolBindingModel) {
        final Optional<School> existingSchool = schoolRepository.findByName(schoolBindingModel.getName());
        if (existingSchool.isPresent()) {
            log.error(SCHOOL_ALREADY_REGISTED_ERROR_TEMPLATE, schoolBindingModel.getName(),
                    schoolBindingModel.getAddress());

            throw new IllegalArgumentException();
        }

        final User principal = userService.findById(schoolBindingModel.getPrincipalId()).orElseThrow();
        School school = modelMapper.map(schoolBindingModel, School.class);
        school.setPrincipal(principal);
        return schoolRepository.save(school);
    }

    @Override
    public void setPrincipal(long schoolId, long userId) {
        final School school = schoolRepository.findById(schoolId)
                .orElseThrow();
        final User user = userService.findById(userId)
                .orElseThrow();

        userService.promoteToPrincipal(user);
        school.setPrincipal(modelMapper.map(user, User.class));
        schoolRepository.saveAndFlush(school);
    }

    @Override
    public List<School> findAll() {
        return schoolRepository.findAll();
    }

    @Override
    public void update(long schoolId, SchoolBindingModel schoolBindingModel) {
        final School school = schoolRepository.findById(schoolId)
                .orElseThrow();
        school.setAddress(schoolBindingModel.getAddress());
        school.setName(schoolBindingModel.getName());

        schoolRepository.save(school);
    }
}