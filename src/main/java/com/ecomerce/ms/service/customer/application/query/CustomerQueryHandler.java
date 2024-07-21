package com.ecomerce.ms.service.customer.application.query;

import com.ecomerce.ms.service.customer.domain.aggregate.customer.Customer;
import com.ecomerce.ms.service.customer.domain.aggregate.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerQueryHandler {

    private final CustomerRepository customerRepository;

    public boolean checkIfSatisfyMakingOrderCondition(UUID customerId) {
        Optional<Customer> customer =  customerRepository.findById(customerId);
        return customer.map(Customer::satisfyOrderMakingCondition).orElse(false);
    }
}
