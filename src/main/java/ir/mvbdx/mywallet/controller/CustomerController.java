package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Customer;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = {"/api/customer", "/api/customers"})
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Customer save(@RequestBody Customer customer){
        return customerService.save(customer);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id){
        return customerService.findById(id);
    }

    @GetMapping("/")
    public List<Customer> findAll(){
        return customerService.findAll();
    }

    @PutMapping("/edit/{id}")
    public Customer update(@PathVariable("id") Long id, @RequestBody Customer customer){
        return customerService.update(id, customer);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable("id") Long id){
        return customerService.delete(id);
    }

}
