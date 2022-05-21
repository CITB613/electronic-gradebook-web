package bg.nbu.gradebook.domain.models.bindings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ClassBindingModel {
    @Range(min = 1, max = 12)
    private int grade;

    @NotNull
    private String group;
}
