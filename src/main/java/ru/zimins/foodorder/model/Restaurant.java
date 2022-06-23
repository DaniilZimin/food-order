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
@Table(name = "restaurant")
public class Restaurant extends AbstractLongPersistable {

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_chain_id")
    private RestaurantChain restaurantChain;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @NotBlank
    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItemCategory> menuItemCategories;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuItem> menuItems;

    public void addMenuItem(MenuItem menuItem) {
        if (menuItems == null) {
            menuItems = new ArrayList<>();
        }
        menuItems.add(menuItem);
        menuItem.setRestaurant(this);
    }

    public void addMenuItemCategory(MenuItemCategory menuItemCategory) {
        if (menuItemCategories == null) {
            menuItemCategories = new ArrayList<>();
        }
        menuItemCategories.add(menuItemCategory);
        menuItemCategory.setRestaurant(this);
    }
}
