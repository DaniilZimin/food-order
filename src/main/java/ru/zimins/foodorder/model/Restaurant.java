package ru.zimins.foodorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zimins.foodorder.model.common.AbstractLongPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractLongPersistable {

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_chain_id")
    private RestaurantChain restaurantChain;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @NotBlank
    @Column(name = "address")
    private String address;
}
