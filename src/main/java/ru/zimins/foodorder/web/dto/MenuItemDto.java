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

    private Double kcal;

    private Double proteins;

    private Double fats;

    private Double carbohydrates;

    private MenuItemCategoryDto menuItemCategory;

    private RestaurantDto restaurant;
}
