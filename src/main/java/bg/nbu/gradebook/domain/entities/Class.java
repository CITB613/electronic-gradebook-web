package bg.nbu.gradebook.domain.entities;

import static java.util.Collections.emptySet;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "classes")
public class Class extends BaseEntity {
    @ManyToMany(targetEntity = Subject.class, fetch = EAGER)
    private Set<Subject> subjects = emptySet();

    @OneToMany(cascade = ALL, orphanRemoval = false)
    @JoinColumn(name = "user_id")
    private Set<User> students = emptySet();
}
