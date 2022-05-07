package bg.nbu.gradebook.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.nbu.gradebook.domain.entities.School;
import bg.nbu.gradebook.domain.models.bindings.SchoolBindingModel;
import bg.nbu.gradebook.domain.models.service.SchoolServiceModel;
import bg.nbu.gradebook.services.schools.SchoolService;

@RestController
@RequestMapping("schools")
public class SchoolController {
    private final SchoolService schoolService;
    private final ModelMapper modelMapper;

    @Autowired
    public SchoolController(SchoolService schoolService, ModelMapper modelMapper) {
        this.schoolService = schoolService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public SchoolBindingModel registerSchool(@Valid SchoolBindingModel schoolBindingModel) {
        final School registerdSchool = schoolService
                .registerSchool(modelMapper.map(schoolBindingModel, SchoolServiceModel.class));

        return modelMapper.map(registerdSchool, SchoolBindingModel.class);
    }
}
