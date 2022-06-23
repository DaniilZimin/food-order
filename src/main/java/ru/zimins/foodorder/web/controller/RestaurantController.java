package ru.zimins.foodorder.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.zimins.foodorder.model.Restaurant;
import ru.zimins.foodorder.service.RestaurantService;
import ru.zimins.foodorder.web.dto.RestaurantDto;
import ru.zimins.foodorder.web.dto.common.PageDto;
import ru.zimins.foodorder.web.mapper.RestaurantMapper;

import java.util.List;

@Tag(name = "RestaurantController", description = "API для управления ресторанами")
@RequestMapping("api/v1/restaurants")
@RestController
public class RestaurantController {

    private final RestaurantService service;

    private final RestaurantMapper mapper;

    @Autowired
    public RestaurantController(RestaurantService service, RestaurantMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Создание ресторана", description = "Возвращает созданный ресторан")
    @PostMapping
    public ResponseEntity<RestaurantDto> create(@RequestBody @Validated RestaurantDto restaurantDto) {
        Restaurant restaurant = service.create(mapper.toEntity(restaurantDto));

        return ResponseEntity.ok(mapper.toDto(restaurant));
    }

    @Operation(summary = "Полный список ресторанов", description = "Возвращает список всех ресторанов")
    @GetMapping
    public ResponseEntity<List<RestaurantDto>> findAll() {
        List<RestaurantDto> restaurant = mapper.toDto(service.findAll());

        return ResponseEntity.ok(restaurant);
    }

    @Operation(summary = "Ресторан по идентификатору", description = "Возвращает ресторан по указанному идентификатору")
    @GetMapping("{id}")
    public ResponseEntity<RestaurantDto> findById(@PathVariable(name = "id") Long id) {
        RestaurantDto restaurant = mapper.toDto(service.findById(id));

        return ResponseEntity.ok(restaurant);
    }

    @Operation(summary = "Страница ресторанов", description = "Возвращает страницу ресторанов по указанным параметрам")
    @GetMapping("page")
    public ResponseEntity<PageDto<RestaurantDto>> findPage(Pageable pageable) {
        PageDto<RestaurantDto> restaurantPage = mapper.toPageDto(service.findPage(pageable));

        return ResponseEntity.ok(restaurantPage);
    }

    @Operation(summary = "Изменение ресторана", description = "Возвращает изменённый ресторан")
    @PutMapping("{id}")
    public ResponseEntity<RestaurantDto> update(@RequestBody @Validated RestaurantDto restaurantDto, @PathVariable(name = "id") Long id) {
        Restaurant entity = mapper.toEntity(restaurantDto);

        entity.setId(id);

        Restaurant restaurant = service.update(entity);

        return ResponseEntity.ok(mapper.toDto(restaurant));
    }

    @Operation(summary = "Удаление сети ресторанов по идентификатору", description = "Возвращает удалённую сеть ресторанов")
    @DeleteMapping("{id}")
    public ResponseEntity<RestaurantDto> delete(@PathVariable(name = "id") Long id) {
        RestaurantDto restaurant = mapper.toDto(service.deleteById(id));

        return ResponseEntity.ok(restaurant);
    }
}
