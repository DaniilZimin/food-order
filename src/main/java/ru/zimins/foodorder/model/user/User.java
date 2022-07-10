package ru.zimins.foodorder.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.zimins.foodorder.model.common.AbstractLongPersistable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_account")
public class User extends AbstractLongPersistable {

    @NotBlank
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
