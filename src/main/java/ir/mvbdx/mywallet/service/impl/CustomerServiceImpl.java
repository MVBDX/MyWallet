package ir.mvbdx.mywallet.service.impl;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Customer;
import ir.mvbdx.mywallet.entity.Role;
import ir.mvbdx.mywallet.repository.CustomerRepository;
import ir.mvbdx.mywallet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements UserService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer findLoggedInUser(Principal principal) {
        return customerRepository.findByEmail(principal.getName());
    }

    public Set<Account> getAllAccounts(Principal principal) {
        return findLoggedInUser(principal).getAccounts();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = customerRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

}
