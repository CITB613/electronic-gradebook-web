package bg.nbu.gradebook.services.schools;

import java.util.List;

import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.models.bindings.SchoolBindingModel;

public interface SchoolService {
    School registerSchool(SchoolBindingModel schoolBindingModel);

    void setPrincipal(long schoolId, long userId);

    List<School> findAll();

    void update(long schoolId, SchoolBindingModel schoolBindingModel);
}