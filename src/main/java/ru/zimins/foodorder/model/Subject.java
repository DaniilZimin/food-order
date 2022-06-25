package ru.zimins.foodorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zimins.foodorder.model.common.AbstractLongPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subject")
public class Subject extends AbstractLongPersistable {

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SubjectType type;

    @NotNull
    @Column(name = "code_okato")
    private Integer codeOkato;

    @OneToMany(mappedBy = "subject")
    List<City> cities;

    public void addCity(City city) {
        if (cities == null) {
            cities = new ArrayList<>();
        }
        cities.add(city);
        city.setSubject(this);
    }
}
