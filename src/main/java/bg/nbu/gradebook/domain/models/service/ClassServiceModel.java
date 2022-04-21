package bg.nbu.gradebook.domain.models.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClassServiceModel extends BaseServiceModel {
    @Range(min = 1, max = 12)
    private int grade;

    @NotNull
    @NotEmpty
    private String group;
}