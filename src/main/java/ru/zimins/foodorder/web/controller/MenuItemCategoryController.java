package ru.zimins.foodorder.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.zimins.foodorder.model.MenuItemCategory;
import ru.zimins.foodorder.service.MenuItemCategoryService;
import ru.zimins.foodorder.web.dto.MenuItemCategoryDto;
import ru.zimins.foodorder.web.dto.common.PageDto;
import ru.zimins.foodorder.web.mapper.MenuItemCategoryMapper;

import java.util.List;

@Tag(name = "MenuItemCategoryController", description = "API для управления категориями пунктов меню")
@RequestMapping("api/v1/menu-item-categories")
@RestController
public class MenuItemCategoryController {

    private final MenuItemCategoryService service;

    private final MenuItemCategoryMapper mapper;

    @Autowired
    public MenuItemCategoryController(MenuItemCategoryService service, MenuItemCategoryMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Создание категории пунктов меню", description = "Возвращает созданную категорию пунктов меню")
    @PostMapping
    public ResponseEntity<MenuItemCategoryDto> create(@RequestBody @Validated MenuItemCategoryDto menuItemCategoryDto) {
        MenuItemCategory menuItemCategory = service.create(mapper.toEntity(menuItemCategoryDto));

        return ResponseEntity.ok(mapper.toDto(menuItemCategory));
    }

    @Operation(summary = "Полный список категорий пунктов меню", description = "Возвращает список всех категорий пунктов меню")
    @GetMapping
    public ResponseEntity<List<MenuItemCategoryDto>> findAll() {
        List<MenuItemCategoryDto> menuItemCategoryList = mapper.toDto(service.findAll());

        return ResponseEntity.ok(menuItemCategoryList);
    }

    @Operation(summary = "Страница категорий пунктов меню", description = "Возвращает страницу категорий пунктов меню по указанным параметрам")
    @GetMapping("page")
    public ResponseEntity<PageDto<MenuItemCategoryDto>> findPage(@RequestParam String nameFilter, Pageable pageable) {
        PageDto<MenuItemCategoryDto> menuItemCategoryPage = mapper.toPageDto(service.findPage(nameFilter, pageable));

        return ResponseEntity.ok(menuItemCategoryPage);
    }

    @Operation(summary = "Изменение категории пунктов меню", description = "Возвращает изменённую категорию пунктов меню")
    @PutMapping("{id}")
    public ResponseEntity<MenuItemCategoryDto> update(@RequestBody @Validated MenuItemCategoryDto menuItemCategoryDto, @PathVariable(name = "id") Long id) {
        menuItemCategoryDto.setId(id);

        MenuItemCategory menuItemCategory = service.update(mapper.toEntity(menuItemCategoryDto));

        return ResponseEntity.ok(mapper.toDto(menuItemCategory));
    }

    @Operation(summary = "Удаление пункта меню по идентификатору", description = "Возвращает удалённый пункт меню")
    @DeleteMapping("{id}")
    public ResponseEntity<MenuItemCategoryDto> delete(@PathVariable(name = "id") Long id) {
        MenuItemCategoryDto menuItemCategory = mapper.toDto(service.deleteById(id));

        return ResponseEntity.ok(menuItemCategory);
    }
}
