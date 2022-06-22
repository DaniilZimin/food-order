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
@Table(name = "city")
public class City extends AbstractLongPersistable {

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @NotBlank
    private Subject subject;

    @OneToMany(mappedBy = "city")
    private List<Restaurant> restaurants;

    public void addRestaurant(Restaurant restaurant) {
        if (restaurants == null) {
            restaurants = new ArrayList<>();
        }
        restaurants.add(restaurant);
        restaurant.setCity(this);
    }
}
