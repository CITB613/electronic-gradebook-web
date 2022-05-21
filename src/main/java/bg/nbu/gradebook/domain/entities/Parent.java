package bg.nbu.gradebook.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Entity
@Getter
@Setter
public class Parent extends User {
    @OneToMany
    private Set<Student> kids;

    Parent() {
        super();

        this.kids = new HashSet<>();
    }


}
