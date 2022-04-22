package bg.nbu.gradebook.domain.models.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserServiceModel extends BaseServiceModel {
    @NotEmpty
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotEmpty
    @NotNull
    private String firstName;

    @NotEmpty
    @NotNull
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    private Set<RoleServiceModel> authorities = new HashSet<>();
}