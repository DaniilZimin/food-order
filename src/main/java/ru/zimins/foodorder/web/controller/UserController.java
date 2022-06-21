package ru.zimins.foodorder.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zimins.foodorder.service.UserService;
import ru.zimins.foodorder.web.dto.UserDto;
import ru.zimins.foodorder.web.dto.common.PageDto;
import ru.zimins.foodorder.web.mapper.UserMapper;

import java.util.List;

@Tag(name = "UserController", description = "API для управления пользователями")
@RequestMapping("api/v1/users")
@RestController
public class UserController {

    private final UserService service;

    private final UserMapper mapper;

    @Autowired
    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Полный список пользователей", description = "Возвращает список всех пользователей системы")
    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = mapper.toDto(service.findAll());

        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Пользователь по идентификатору", description = "Возвращает пользователя по указанному идентификатору")
    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(@PathVariable(name = "id") Long id) {
        UserDto user = mapper.toDto(service.findById(id));

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Страница пользователей", description = "Возвращает страницу пользователей по указанным параметрам")
    @GetMapping("page")
    public ResponseEntity<PageDto<UserDto>> findById(Pageable pageable) {
        PageDto<UserDto> userPage = mapper.toPageDto(service.findPage(pageable));

        return ResponseEntity.ok(userPage);
    }

}
