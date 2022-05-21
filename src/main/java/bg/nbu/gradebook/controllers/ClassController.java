package bg.nbu.gradebook.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.nbu.gradebook.domain.models.bindings.ClassBindingModel;
import bg.nbu.gradebook.domain.models.service.ClassServiceModel;
import bg.nbu.gradebook.services.classes.ClassService;

@RestController
@RequestMapping("classes")
public class ClassController {
    private final ClassService classService;

    @Autowired
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @PostMapping
    public ClassServiceModel create(@Valid ClassBindingModel classData) {
        return classService.create(classData);
    }

    @PostMapping("/{classId}/enrollment/students/{studentId}")
    public void enrollStudent(@PathVariable long classId, @PathVariable long studentId) {
        classService.enrollStudentInClass(studentId, classId);
    }

    @DeleteMapping("/{classId}/enrollment/students/{studentId}")
    public void unenrollStudent(@PathVariable long classId, @PathVariable long studentId) {
        classService.unenrollStudentFromClass(studentId, classId);
    }

    @PostMapping("/{classId}/subjects/{subjectId}")
    public void addSubject(@PathVariable long classId, @PathVariable long subjectId) {
        classService.addSubject(classId, subjectId);
    }

    @DeleteMapping("/{classId}/subjects/{subjectId}")
    public void removeSubject(@PathVariable long classId, @PathVariable long subjectId) {
        classService.removeSubject(classId, subjectId);
    }

    @PutMapping("/students/{studentId}")
    public ClassServiceModel updateStudentClass(@PathVariable long studentId,
            @Valid ClassBindingModel classBindingModel) {
        return classService.updateStudentClass(studentId, classBindingModel);
    }
}
