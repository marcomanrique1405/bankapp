package com.marco.bankapp.auth.mapper;

import com.marco.bankapp.auth.dto.response.RegisterResponse;
import com.marco.bankapp.auth.entity.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public RegisterResponse toRegisterResponse(AppUser appUser) {

        return RegisterResponse.builder()
                .id(appUser.getId())
                .email(appUser.getEmail())
                .roles(appUser.getRoles())
                .status(appUser.getStatus())
                .build();

    }

}
