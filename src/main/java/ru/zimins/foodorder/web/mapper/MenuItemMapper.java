package ru.zimins.foodorder.web.mapper;

import org.mapstruct.Mapper;
import ru.zimins.foodorder.model.MenuItem;
import ru.zimins.foodorder.web.dto.MenuItemDto;
import ru.zimins.foodorder.web.mapper.common.WebMapper;
import ru.zimins.foodorder.web.mapper.config.WebMapperConfig;

@Mapper(config = WebMapperConfig.class)
public interface MenuItemMapper extends WebMapper<MenuItem, MenuItemDto> {
}
