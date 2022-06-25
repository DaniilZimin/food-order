package ru.zimins.foodorder.web.dto;

import lombok.Data;
import ru.zimins.foodorder.model.SubjectType;

@Data
public class SubjectDto {

    private Long id;

    private String name;

    private SubjectType type;

    private Integer codeOkato;
}
