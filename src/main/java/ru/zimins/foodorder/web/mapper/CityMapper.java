package ru.zimins.foodorder.web.mapper;

import org.mapstruct.Mapper;
import ru.zimins.foodorder.model.City;
import ru.zimins.foodorder.web.dto.CityDto;
import ru.zimins.foodorder.web.mapper.common.WebMapper;
import ru.zimins.foodorder.web.mapper.config.WebMapperConfig;

@Mapper(config = WebMapperConfig.class)
public interface CityMapper extends WebMapper<City, CityDto> {
}