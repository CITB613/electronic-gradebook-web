package bg.nbu.gradebook.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "subjects", uniqueConstraints = @UniqueConstraint(columnNames = { "name", "teacher_id" }))
@EqualsAndHashCode(callSuper = false)
public class Subject extends BaseEntity {
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToOne()
    @JoinColumn(name = "teacher_id")
    private User teacher;
}
