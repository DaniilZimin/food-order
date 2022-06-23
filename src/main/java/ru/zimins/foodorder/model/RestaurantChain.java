package ru.zimins.foodorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zimins.foodorder.model.common.AbstractLongPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant_chain")
public class RestaurantChain extends AbstractLongPersistable {

    @NotBlank
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "restaurantChain")
    private List<Restaurant> restaurants;

    public void addRestaurant(Restaurant restaurant) {
        if (restaurants == null) {
           restaurants = new ArrayList<>();
        }
        restaurants.add(restaurant);
        restaurant.setRestaurantChain(this);
    }
}
