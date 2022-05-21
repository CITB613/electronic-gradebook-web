package bg.nbu.gradebook.services.schools;

import java.util.List;

import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.models.service.SchoolServiceModel;

public interface SchoolService {
    School registerSchool(SchoolServiceModel schoolServiceModel);

    void setPrincipal(long schoolId, long userId);

    List<School> findAll();
}