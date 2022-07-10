package ru.zimins.foodorder.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.zimins.foodorder.model.user.User;
import ru.zimins.foodorder.service.UserService;
import ru.zimins.foodorder.web.dto.UserDto;
import ru.zimins.foodorder.web.dto.common.PageDto;
import ru.zimins.foodorder.web.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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

    @Operation(summary = "Регистрация", description = "Добавление нового пользователя в систему")
    @PostMapping("registration")
    public ResponseEntity<UserDto> registration(@RequestBody @Validated UserDto userDto) {
        User user = mapper.toEntity(userDto);

        return ResponseEntity.ok(mapper.toDto(service.create(user)));
    }

    @Operation(summary = "Информация о текущие пользователе", description = "Возвращает информацию об авторизованном пользователе, который делает запрос")
    @GetMapping("info")
    public ResponseEntity<Principal> userInfo(HttpServletRequest request) {
        return ResponseEntity.ok(request.getUserPrincipal());
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
    public ResponseEntity<PageDto<UserDto>> findPage(Pageable pageable) {
        PageDto<UserDto> userPage = mapper.toPageDto(service.findPage(pageable));

        return ResponseEntity.ok(userPage);
    }

}
