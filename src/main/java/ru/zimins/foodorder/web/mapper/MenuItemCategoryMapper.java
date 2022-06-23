package ru.zimins.foodorder.web.mapper;

import org.mapstruct.Mapper;
import ru.zimins.foodorder.model.MenuItemCategory;
import ru.zimins.foodorder.web.dto.MenuItemCategoryDto;
import ru.zimins.foodorder.web.mapper.common.WebMapper;
import ru.zimins.foodorder.web.mapper.config.WebMapperConfig;

@Mapper(config = WebMapperConfig.class)
public interface MenuItemCategoryMapper extends WebMapper<MenuItemCategory, MenuItemCategoryDto> {
}
