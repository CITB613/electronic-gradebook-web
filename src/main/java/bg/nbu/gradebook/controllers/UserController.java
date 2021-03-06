package bg.nbu.gradebook.controllers;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.UserBindingModel;
import bg.nbu.gradebook.domain.models.service.UserServiceModel;
import bg.nbu.gradebook.services.users.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final Mapper mapper;

    @Autowired
    public UserController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<UserServiceModel> fetchAll() {
        return userService.findAll();
    }

    @PutMapping("/{userId}")
    public void updatePersonalDetails(@PathVariable long userId, @RequestBody UserBindingModel userBindingModel) {
        userService.update(userId, mapper.map(userBindingModel, User.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable(name = "id") long id) {
        userService.delete(id);
    }

    @GetMapping("/principals")
    public List<User> findAllPrincipals() {
        return userService.findAllPrincipals();
    }

    @GetMapping("/teachers")
    public List<User> findAllTeachers() {
        return userService.findAllTeachers();
    }

    @GetMapping("/students")
    public List<User> findAllStudents() {
        return userService.findAllStudents();
    }
}
