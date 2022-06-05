package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer save(Customer customer);

    Customer findById(Long id);

    List<Customer> findAll();

    Customer update(Long id, Customer customer);

    boolean delete(Long id);
}
