package bg.nbu.gradebook.domain.models.service;

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
public class SchoolServiceModel extends BaseServiceModel {
    @NotNull
    @NotEmpty
    private String name;
    
    @NotNull
    @NotEmpty
    private String address;
    
}