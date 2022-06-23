package ru.zimins.foodorder.web.dto;

import lombok.Data;
import ru.zimins.foodorder.model.Unit;

@Data
public class MenuItemDto {

    private Long id;

    private String name;

    private String description;

    private Integer price;

    private Double weight;

    private Unit unit;

    private String composition;

    private Short kcal;

    private Short proteins;

    private Short fats;

    private Short carbohydrates;

    private MenuItemCategoryDto menuItemCategory;

    private RestaurantDto restaurant;
}
