package com.marco.bankapp.auth.dto.response;

import com.marco.bankapp.auth.enums.Role;
import com.marco.bankapp.auth.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
public class RegisterResponse {

    private UUID id;

    private String email;

    private Set<Role> roles;

    private UserStatus status;

}
