package ru.zimins.foodorder.web.dto;

import lombok.Data;

@Data
public class CityDto {

    private Long id;

    private String name;

    private SubjectDto subject;
}
