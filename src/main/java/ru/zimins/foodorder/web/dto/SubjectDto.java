package ru.zimins.foodorder.web.dto;

import lombok.Data;
import ru.zimins.foodorder.model.TypeSubject;

@Data
public class SubjectDto {

    private Long id;

    private String name;

    private TypeSubject type;

    private int codeOKATO;
}
