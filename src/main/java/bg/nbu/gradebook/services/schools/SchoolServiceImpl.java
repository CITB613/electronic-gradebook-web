package bg.nbu.gradebook.services.schools;

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
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository, ModelMapper modelMapper, UserService userService) {
        this.schoolRepository = schoolRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public void registerSchool(SchoolServiceModel schoolServiceModel) {
        schoolRepository.findByName(schoolServiceModel.getName())
                .ifPresentOrElse(
                        school -> log.error(SCHOOL_ALREADY_REGISTED_ERROR_TEMPLATE, school.getName(),
                                school.getAddress()),
                        () -> schoolRepository.saveAndFlush(modelMapper.map(schoolServiceModel, School.class)));
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