package bg.nbu.gradebook.domain.models.bindings;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserBindingModel {
    private String firstName;

    private String lastName;

    private LocalDate birthDate;
}
