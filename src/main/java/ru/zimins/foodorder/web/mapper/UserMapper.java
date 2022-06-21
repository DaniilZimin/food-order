package ru.zimins.foodorder.web.mapper;

import org.mapstruct.Mapper;
import ru.zimins.foodorder.model.User;
import ru.zimins.foodorder.web.dto.UserDto;
import ru.zimins.foodorder.web.mapper.common.WebMapper;
import ru.zimins.foodorder.web.mapper.config.WebMapperConfig;

@Mapper(config = WebMapperConfig.class)
public interface UserMapper extends WebMapper<User, UserDto> {
}