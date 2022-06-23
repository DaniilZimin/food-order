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
@Table(name = "menu_item_category")
public class MenuItemCategory extends AbstractLongPersistable {

    @NotBlank
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "menuItemCategory")
    private List<MenuItem> menuItems;

    public void addMenuItem(MenuItem menuItem) {
        if (menuItems == null) {
            menuItems = new ArrayList<>();
        }
        menuItems.add(menuItem);
        menuItem.setMenuItemCategory(this);
    }
}
