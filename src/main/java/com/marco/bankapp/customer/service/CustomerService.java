package com.marco.bankapp.customer.service;

import com.marco.bankapp.customer.entity.Customer;
import com.marco.bankapp.customer.enums.CustomerStatus;
import com.marco.bankapp.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer createForRegistration(
            UUID userId,
            String firstName,
            String lastName,
            String phone,
            LocalDate dateOfBirth
    ) {

        Instant now = Instant.now();

        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone)
                .dateOfBirth(dateOfBirth)
                .status(CustomerStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Customer customerGuardado = customerRepository.save(customer);

        return customerGuardado;

    }

}
