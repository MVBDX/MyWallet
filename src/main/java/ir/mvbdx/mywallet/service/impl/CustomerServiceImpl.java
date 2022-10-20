package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.model.entity.Account;
import ir.mvbdx.mywallet.model.entity.Customer;
import ir.mvbdx.mywallet.model.entity.Role;
import ir.mvbdx.mywallet.repository.CustomerRepository;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Customer findCustomer(Principal principal) {
        return customerRepository.findByEmail(principal.getName());
    }

    @Override
    public List<Account> accountsOfCustomer(Principal principal) {
        return findCustomer(principal).getAccounts().stream().
                sorted((Comparator.comparing(Account::getBalance)).reversed()).collect(Collectors.toList());
    }

    @Override
    public Customer save(Customer customer) {
//        if (customerRepository.findByEmail(customer.getUsername()).isPresent())
//            throw new UsernameExistException("کاربری با این نام کاربری در سیستم وجود دارد!");
//        customer.setRoles(List.of(new Role(RoleType.ROLE_CUSTOMER.name(), null)));
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = customerRepository.findByEmail(username);
        if (user == null)
            throw new UsernameNotFoundException("Invalid username or password.");
        return new User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
