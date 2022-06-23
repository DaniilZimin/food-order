package ru.zimins.foodorder.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zimins.foodorder.service.CityService;
import ru.zimins.foodorder.web.dto.CityDto;
import ru.zimins.foodorder.web.dto.common.PageDto;
import ru.zimins.foodorder.web.mapper.CityMapper;

import java.util.List;

@Tag(name = "CityController", description = "API для получения городов")
@RequestMapping("api/v1/cities")
@RestController
public class CityController {

    private final CityService service;

    private final CityMapper mapper;

    @Autowired
    public CityController(CityService service, CityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(summary = "Полный список городов", description = "Возвращает список всех городов системы")
    @GetMapping
    public ResponseEntity<List<CityDto>> findAll() {
        List<CityDto> cities = mapper.toDto(service.findAll());

        return ResponseEntity.ok(cities);
    }

    @Operation(summary = "Город по идентификатору", description = "Возвращает город по указанному идентификатору")
    @GetMapping("{id}")
    public ResponseEntity<CityDto> findById(@PathVariable(name = "id") Long id) {
        CityDto user = mapper.toDto(service.findById(id));

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Страница городов", description = "Возвращает страницу гоордов по указанным параметрам")
    @GetMapping("page")
    public ResponseEntity<PageDto<CityDto>> findPage(@RequestParam(required = false) String nameFilter, Pageable pageable) {
        PageDto<CityDto> cityPage = mapper.toPageDto(service.findPage(nameFilter, pageable));

        return ResponseEntity.ok(cityPage);
    }
}
