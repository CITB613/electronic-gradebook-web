package bg.nbu.gradebook.controllers;

import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.nbu.gradebook.services.classes.ClassService;

import javax.validation.Valid;

@RestController
@RequestMapping("classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping("/create")
    public ClassServiceModel create(@Valid ClassBindingModel classData) {
        return classService.create(classData);
    }

    @PostMapping("/{classId}/enroll/{studentId}")
    public void enrollStudent(@PathVariable long classId, @PathVariable long studentId) {
        classService.enrollStudentInClass(studentId, classId);
    }

    @DeleteMapping("/{classId}/student/{studentId}")
    public void unenrollStudent(@PathVariable long classId, @PathVariable long studentId) {
        classService.unenrollStudentFromClass(studentId, classId);
    }
}
