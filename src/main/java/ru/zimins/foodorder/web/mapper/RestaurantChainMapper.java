package ru.zimins.foodorder.web.mapper;

import org.mapstruct.Mapper;
import ru.zimins.foodorder.model.RestaurantChain;
import ru.zimins.foodorder.web.dto.RestaurantChainDto;
import ru.zimins.foodorder.web.mapper.common.WebMapper;
import ru.zimins.foodorder.web.mapper.config.WebMapperConfig;

@Mapper(config = WebMapperConfig.class)
public interface RestaurantChainMapper extends WebMapper<RestaurantChain, RestaurantChainDto> {
}
