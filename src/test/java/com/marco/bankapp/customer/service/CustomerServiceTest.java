package com.marco.bankapp.customer.service;

import com.marco.bankapp.customer.entity.Customer;
import com.marco.bankapp.customer.enums.CustomerStatus;
import com.marco.bankapp.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createForRegistration_shouldCreateAndSaveActiveCustomer() {
        UUID userId = UUID.randomUUID();
        String firstName = "Marco";
        String lastName = "Manrique";
        String phone = "7471234567";
        LocalDate dateOfBirth = LocalDate.of(2002, 5, 14);

        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Customer result = customerService.createForRegistration(
                userId,
                firstName,
                lastName,
                phone,
                dateOfBirth
        );

        assertNotNull(result.getId());
        assertEquals(userId, result.getUserId());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(phone, result.getPhone());
        assertEquals(dateOfBirth, result.getDateOfBirth());
        assertEquals(CustomerStatus.ACTIVE, result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        assertEquals(result.getCreatedAt(), result.getUpdatedAt());
        verify(customerRepository).save(any(Customer.class));
    }




}