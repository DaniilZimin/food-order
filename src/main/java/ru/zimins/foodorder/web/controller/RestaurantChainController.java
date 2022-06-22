package ru.zimins.foodorder.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.zimins.foodorder.model.RestaurantChain;
import ru.zimins.foodorder.service.RestaurantChainService;
import ru.zimins.foodorder.web.dto.RestaurantChainDto;
import ru.zimins.foodorder.web.dto.common.PageDto;
import ru.zimins.foodorder.web.mapper.RestaurantChainMapper;

import java.util.List;

@Tag(name = "RestaurantChainController", description = "API для управления сетями ресторанов")
@RequestMapping("api/v1/restaurant-chains")
@RestController
public class RestaurantChainController {

    private final RestaurantChainService service;

    private final RestaurantChainMapper mapper;

    @Autowired
    public RestaurantChainController(RestaurantChainService service, RestaurantChainMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Создание сети ресторанов", description = "Возвращает созданную сеть ресторанов")
    @PostMapping
    public ResponseEntity<RestaurantChainDto> create(@RequestBody @Validated RestaurantChainDto restaurantChainDto) {
        RestaurantChain restaurantChain = service.create(mapper.toEntity(restaurantChainDto));

        return ResponseEntity.ok(mapper.toDto(restaurantChain));
    }

    @Operation(summary = "Полный список сетей ресторанов", description = "Возвращает список всех сетей ресторанов")
    @GetMapping
    public ResponseEntity<List<RestaurantChainDto>> findAll() {
        List<RestaurantChainDto> restaurantChains = mapper.toDto(service.findAll());

        return ResponseEntity.ok(restaurantChains);
    }

    @Operation(summary = "Сеть ресторанов по идентификатору", description = "Возвращает сеть ресторанов по указанному идентификатору")
    @GetMapping("{id}")
    public ResponseEntity<RestaurantChainDto> findById(@PathVariable(name = "id") Long id) {
        RestaurantChainDto restaurantChain = mapper.toDto(service.findById(id));

        return ResponseEntity.ok(restaurantChain);
    }

    @Operation(summary = "Страница сетей ресторанов", description = "Возвращает страницу сетей ресторанов по указанным параметрам")
    @GetMapping("page")
    public ResponseEntity<PageDto<RestaurantChainDto>> findPage(Pageable pageable) {
        PageDto<RestaurantChainDto> restaurantChainPage = mapper.toPageDto(service.findPage(pageable));

        return ResponseEntity.ok(restaurantChainPage);
    }

    @Operation(summary = "Изменение сети ресторанов", description = "Возвращает изменённую сеть ресторанов")
    @PutMapping("{id}")
    public ResponseEntity<RestaurantChainDto> update(@RequestBody @Validated RestaurantChainDto restaurantChainDto, @PathVariable(name = "id") Long id) {
        RestaurantChain entity = mapper.toEntity(restaurantChainDto);
        entity.setId(id);
        RestaurantChain restaurantChain = service.update(entity);

        return ResponseEntity.ok(mapper.toDto(restaurantChain));
    }

    @Operation(summary = "Удаление сети ресторанов по идентификатору", description = "Возвращает удалённую сеть ресторанов")
    @DeleteMapping("{id}")
    public ResponseEntity<RestaurantChainDto> delete(@PathVariable(name = "id") Long id) {
        RestaurantChainDto restaurantChain = mapper.toDto(service.deleteById(id));

        return ResponseEntity.ok(restaurantChain);
    }
}
