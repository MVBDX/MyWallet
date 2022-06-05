package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Customer;
import ir.mvbdx.mywallet.exception.CustomerNotFoundException;
import ir.mvbdx.mywallet.repository.CustomerRepository;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer update(Long id, Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty())
            throw new CustomerNotFoundException(id);
        Customer updateCustomer = optionalCustomer.get();
        updateCustomer.setPassword(customer.getPassword());
        updateCustomer.setFirstName(customer.getFirstName());
        updateCustomer.setLastName(customer.getLastName());
        updateCustomer.setEmail(customer.getEmail());
        updateCustomer.setPhone(customer.getPhone());
        return customerRepository.save(customer);
    }

    @Override
    public boolean delete(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty())
            throw new CustomerNotFoundException(id);
        customerRepository.deleteById(id);
        return true;
    }
}