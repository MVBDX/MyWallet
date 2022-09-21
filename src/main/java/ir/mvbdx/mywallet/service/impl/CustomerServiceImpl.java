package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Customer;
import ir.mvbdx.mywallet.entity.Role;
import ir.mvbdx.mywallet.repository.CustomerRepository;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

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
