package bg.nbu.gradebook.controllers;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.nbu.gradebook.domain.models.service.SubjectServiceModel;
import bg.nbu.gradebook.services.subjects.SubjectService;

@RestController
@RequestMapping("/subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    public SubjectServiceModel createSubject(@RequestBody @Valid SubjectServiceModel subjectServiceModel) {
        return subjectService.createSubject(subjectServiceModel);
    }

    @GetMapping
    public List<SubjectServiceModel> getAllSubjects() {
        return subjectService.findAll();
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable(name = "id") long id) {
        subjectService.deleteById(id);
    }
}
