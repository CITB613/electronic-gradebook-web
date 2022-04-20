package bg.nbu.gradebook.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "subjects")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Subject extends BaseEntity {
    @EqualsAndHashCode.Include
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "teacher", nullable = false)
    private User teacher;
}
