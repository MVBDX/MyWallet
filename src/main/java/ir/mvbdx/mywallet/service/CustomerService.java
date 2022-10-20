package ir.mvbdx.mywallet.service;

import ir.mvbdx.mywallet.model.entity.Account;
import ir.mvbdx.mywallet.model.entity.Customer;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Principal;
import java.util.List;

public interface CustomerService extends UserDetailsService {
    Customer findCustomer(Principal principal);

    List<Account> accountsOfCustomer(Principal principal);

    Customer save(Customer customer);
}