package bg.nbu.gradebook.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.Class;
import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.models.bindings.SchoolBindingModel;
import bg.nbu.gradebook.services.schools.SchoolService;

// FIXME Route permissions by role

@RestController
@RequestMapping("schools")
public class SchoolController {
    private final SchoolService schoolService;
    private final Mapper mapper;

    @Autowired
    public SchoolController(SchoolService schoolService, Mapper mapper) {
        this.schoolService = schoolService;
        this.mapper = mapper;
    }

    @PostMapping
    public SchoolBindingModel registerSchool(@RequestBody @Valid SchoolBindingModel schoolBindingModel) {
        final School registerdSchool = schoolService.registerSchool(schoolBindingModel);

        return mapper.map(registerdSchool, SchoolBindingModel.class);
    }

    @PostMapping("/{schoolId}/principal/{userId}")
    public void setPrincipal(@PathVariable long schoolId, @PathVariable long userId) {
        schoolService.setPrincipal(schoolId, userId);
    }

    @GetMapping
    public List<SchoolBindingModel> getAllSchools() {
        return mapper.mapCollection(schoolService.findAll(), SchoolBindingModel.class);
    }

    @GetMapping("/{schoolId}/classes")
    public List<Class> getAllClassesBySchool(@PathVariable long schoolId) {
        final School school = schoolService.findAll()
                .stream()
                .filter(currentSchool -> currentSchool.getId() == schoolId)
                .findFirst()
                .orElseThrow();

        return school.getClasses();
    }

    @PutMapping("/{schoolId}")
    public void updateSchool(@PathVariable long schoolId, @RequestBody @Valid SchoolBindingModel schoolBindingModel) {
        schoolService.update(schoolId, schoolBindingModel);
    }

}
