package ru.zimins.foodorder.model.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN,
    ROLE_CLIENT,
    ROLE_TECH_SUPPORT,
    ROLE_SELLER;

    @Override
    public String getAuthority() {
        return name();
    }
}
