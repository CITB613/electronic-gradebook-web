package bg.nbu.gradebook.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Student extends User {
    private int grade;
}
