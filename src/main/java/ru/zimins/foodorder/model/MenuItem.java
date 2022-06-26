package ru.zimins.foodorder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zimins.foodorder.model.common.AbstractLongPersistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "menu_item")
public class MenuItem extends AbstractLongPersistable {

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "price")
    private Integer price;

    @NotNull
    @Column(name = "weight")
    private Double weight;

    @NotNull
    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @NotBlank
    @Column(name = "composition")
    private String composition;

    @NotNull
    @Column(name = "kcal")
    private Double kcal;

    @NotNull
    @Column(name = "proteins")
    private Double proteins;

    @NotNull
    @Column(name = "fats")
    private Double fats;

    @NotNull
    @Column(name = "carbohydrates")
    private Double carbohydrates;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "menu_item_category_id")
    private MenuItemCategory menuItemCategory;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
