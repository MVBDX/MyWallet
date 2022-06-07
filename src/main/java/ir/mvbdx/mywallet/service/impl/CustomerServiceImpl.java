package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer> {
    public CustomerServiceImpl(@Qualifier("customerRepository") JpaRepository<Customer, Long> baseRepository) {
        super(baseRepository);
    }
}
