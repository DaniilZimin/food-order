package ru.zimins.foodorder.web.dto;

import lombok.Data;

@Data
public class RestaurantDto {

    private Long id;

    private String name;

    private RestaurantChainDto restaurantChain;

    private CityDto city;

    private String address;
}
