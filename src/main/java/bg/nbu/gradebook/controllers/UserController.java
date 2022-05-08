package bg.nbu.gradebook.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bg.nbu.gradebook.commons.utils.Mapper;
import bg.nbu.gradebook.domain.entities.User;
import bg.nbu.gradebook.domain.models.bindings.UserBindingModel;
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

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable long userId, UserBindingModel userBindingModel) {
        userService.update(userId, mapper.map(userBindingModel, User.class));
    }
}
