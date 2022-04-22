package bg.nbu.gradebook.domain.entities;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "schools")
public class School extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "address", unique = true, nullable = false)
    private String address;

    @Column(name = "principal", unique = true, nullable = false)
    private User principal;

    @OneToMany(cascade = ALL, orphanRemoval = true)
    private List<Class> classes = new ArrayList<>();
}
