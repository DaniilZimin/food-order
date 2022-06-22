package ru.zimins.foodorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zimins.foodorder.model.common.AbstractLongPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TypeSubject type;

    @NotBlank
    @Column(name = "code_okato")
    private int codeOKATO;

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
