package org.reddy.service;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.reddy.entity.CustomerEntity;
import org.reddy.repository.CustomerRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerEntity> findAll() {
        return this.customerRepository.findAll().list();
    }

    public Optional<CustomerEntity> findById(@NonNull Integer customerId) {
        return customerRepository.findByIdOptional(customerId);
    }

    @Transactional
    public void save(@Valid CustomerEntity customer) {
        log.debug("Saving Customer: {}", customer);
        customerRepository.persist(customer);
    }

    @Transactional
    public void update(@Valid CustomerEntity customer) {
        log.debug("Updating Customer: {}", customer);
        if (Objects.isNull(customer)) {
            throw new ServiceException("Customer does not have a customerId");
        }
        CustomerEntity entity = customerRepository.findByIdOptional(customer.getCustomerId())
                .orElseThrow(() -> new RuntimeException("No Customer found for customerId"));
        customerRepository.persist(entity);
    }

}
