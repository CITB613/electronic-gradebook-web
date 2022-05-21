package bg.nbu.gradebook.domain.entities;

import static java.util.Collections.emptySet;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "classes")
public class Class extends BaseEntity {
    @ManyToMany(fetch = EAGER)
    private Set<Subject> subjects = emptySet();

    @OneToMany(cascade = ALL, orphanRemoval = false)
    private Set<User> students = emptySet();

    @Range(min = 1, max = 12)
    @Column(nullable = false)
    private int grade;

    @Column(name = "gradeId", nullable = false)
    private String group;
}
