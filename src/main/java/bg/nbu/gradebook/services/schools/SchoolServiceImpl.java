package bg.nbu.gradebook.services.schools;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.service.SchoolServiceModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.repositories.SchoolRepository;
import bg.nbu.gradebook.services.users.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SchoolServiceImpl implements SchoolService {
    private static final String SCHOOL_ALREADY_REGISTED_ERROR_TEMPLATE = "School {} located at {} is already registed";

    private final SchoolRepository schoolRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, UserService userService, ModelMapper modelMapper) {
        this.schoolRepository = schoolRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public School registerSchool(SchoolServiceModel schoolServiceModel) {
        final Optional<School> school = schoolRepository.findByName(schoolServiceModel.getName());
        if (school.isEmpty()) {
            log.error(SCHOOL_ALREADY_REGISTED_ERROR_TEMPLATE, schoolServiceModel.getName(),
                    schoolServiceModel.getAddress());

            throw new IllegalArgumentException();
        }

        return schoolRepository.saveAndFlush(modelMapper.map(schoolServiceModel, School.class));
    }

    @Override
    public void setPrincipal(long schoolId, long userId) {
        final School school = schoolRepository.findById(schoolId)
                .orElseThrow();
        final UserServiceModel userServiceModel = userService.findById(userId)
                .orElseThrow();

        userService.promoteToPrincipal(userServiceModel);
        school.setPrincipal(modelMapper.map(userServiceModel, User.class));
        schoolRepository.saveAndFlush(school);
    }
}