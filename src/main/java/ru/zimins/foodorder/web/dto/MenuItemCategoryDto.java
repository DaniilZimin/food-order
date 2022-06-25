package ru.zimins.foodorder.web.dto;

import lombok.Data;

@Data
public class MenuItemCategoryDto {

    private Long id;

    private String name;

    private RestaurantDto restaurant;
}
