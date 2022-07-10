package bg.nbu.gradebook.domain.models.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SubjectServiceModel extends BaseServiceModel {
    @NotNull
    @NotEmpty
    private String name;
    
    private long teacherId;
}