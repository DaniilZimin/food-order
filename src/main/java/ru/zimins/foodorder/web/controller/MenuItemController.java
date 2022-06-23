package ru.zimins.foodorder.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.zimins.foodorder.model.MenuItem;
import ru.zimins.foodorder.service.MenuItemService;
import ru.zimins.foodorder.web.dto.MenuItemDto;
import ru.zimins.foodorder.web.dto.common.PageDto;
import ru.zimins.foodorder.web.mapper.MenuItemMapper;

import java.util.List;

@Tag(name = "MenuItemController", description = "API для управления пунктами меню")
@RequestMapping("api/v1/menu-items")
@RestController
public class MenuItemController {

    private final MenuItemService service;

    private final MenuItemMapper mapper;

    @Autowired
    public MenuItemController(MenuItemService service, MenuItemMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Создание пункта меню", description = "Возвращает созданный пункт меню")
    @PostMapping
    public ResponseEntity<MenuItemDto> create(@RequestBody @Validated MenuItemDto menuItemDto) {
        MenuItem menuItem = service.create(mapper.toEntity(menuItemDto));

        return ResponseEntity.ok(mapper.toDto(menuItem));
    }

    @Operation(summary = "Полный список пунктов меню по идентификатору ресторана",
            description = "Возвращает список всех пунктов меню по указанному идентификатору ресторана")
    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<MenuItemDto>> findAll(@PathVariable(name = "id") Long id) {
        List<MenuItemDto> menuItem = mapper.toDto(service.findAllByRestaurantId(id));

        return ResponseEntity.ok(menuItem);
    }

    @Operation(summary = "Пункт меню по идентификатору", description = "Возвращает пункт меню по указанному идентификатору")
    @GetMapping("{id}")
    public ResponseEntity<MenuItemDto> findById(@PathVariable(name = "id") Long id) {
        MenuItemDto menuItem = mapper.toDto(service.findById(id));

        return ResponseEntity.ok(menuItem);
    }

    @Operation(summary = "Страница пунктов меню", description = "Возвращает страницу пунктов меню по указанным параметрам")
    @GetMapping("page")
    public ResponseEntity<PageDto<MenuItemDto>> findPage(@RequestParam Long id, @RequestParam(required = false) String nameFilter, Pageable pageable) {
        PageDto<MenuItemDto> menuItemPageDto = mapper.toPageDto(service.findPage(id, nameFilter, pageable));

        return ResponseEntity.ok(menuItemPageDto);
    }

    @Operation(summary = "Изменение пункта меню", description = "Возвращает изменённый пункт меню")
    @PutMapping("{id}")
    public ResponseEntity<MenuItemDto> update(@RequestBody @Validated MenuItemDto menuItemDto, @PathVariable(name = "id") Long id) {
        menuItemDto.setId(id);

        MenuItem menuItem = service.update(mapper.toEntity(menuItemDto));

        return ResponseEntity.ok(mapper.toDto(menuItem));
    }

    @Operation(summary = "Удаление пункта меню по идентификатору", description = "Возвращает удалённый пункт меню")
    @DeleteMapping("{id}")
    public ResponseEntity<MenuItemDto> delete(@PathVariable(name = "id") Long id) {
        MenuItemDto menuItem = mapper.toDto(service.deleteById(id));

        return ResponseEntity.ok(menuItem);
    }
}
