package ru.zimins.foodorder.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.zimins.foodorder.model.user.Role;

import java.time.LocalDate;

@Data
public class UserDto {

    private Long id;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String fullName;

    private String email;

    private String phone;

    private LocalDate birthDate;

    private Role role;
}
