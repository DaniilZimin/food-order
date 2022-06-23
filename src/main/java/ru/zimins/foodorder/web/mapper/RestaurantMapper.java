package ru.zimins.foodorder.web.mapper;

import org.mapstruct.Mapper;
import ru.zimins.foodorder.model.Restaurant;
import ru.zimins.foodorder.web.dto.RestaurantDto;
import ru.zimins.foodorder.web.mapper.common.WebMapper;
import ru.zimins.foodorder.web.mapper.config.WebMapperConfig;

@Mapper(config = WebMapperConfig.class)
public interface RestaurantMapper extends WebMapper<Restaurant, RestaurantDto> {
}
