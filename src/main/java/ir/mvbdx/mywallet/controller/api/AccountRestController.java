package ir.mvbdx.mywallet.controller.api;

import ir.mvbdx.mywallet.model.dto.AccountDTO;
import ir.mvbdx.mywallet.model.dto.CustomerDTO;
import ir.mvbdx.mywallet.model.dto.TransactionDTO;
import ir.mvbdx.mywallet.model.entity.Account;
import ir.mvbdx.mywallet.service.AccountService;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class
AccountRestController {
    private final AccountService accountService;
    private final CustomerService customerService;
    private final ModelMapper modelMapper;

    @GetMapping({"/list", "/"})
//    @ResponseStatus(HttpStatus.OK)
    public List<AccountDTO> findAll() {
        return accountService.findAll().stream()
                .map(account -> modelMapper.map(account, AccountDTO.class)).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AccountDTO find(@PathVariable("id") Long id) {
        return modelMapper.map(accountService.findById(id), AccountDTO.class);
    }

    @PostMapping("/save")
    public Account save(@Valid @RequestBody AccountDTO accountDTO, Principal principal) {
        accountDTO.setCustomer(modelMapper.map(customerService.findCustomer(principal), CustomerDTO.class));
        return accountService.save(modelMapper.map(accountDTO, Account.class));
    }

    @PutMapping("/edit")
    public Account update(@Valid @RequestBody AccountDTO accountDTO, Principal principal) {
        accountDTO.setCustomer(modelMapper.map(customerService.findCustomer(principal), CustomerDTO.class));
        return accountService.update(accountDTO.getId(), modelMapper.map(accountDTO, Account.class));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> deleteApi(@PathVariable("id") Long id) {
        if (!accountService.delete(id))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/{id}/transactions")
    public Set<TransactionDTO> findAllTransactions(@PathVariable("id") Long id){
        return accountService.getTransactions(id).stream()
                .map(transaction -> modelMapper.map(transaction,TransactionDTO.class)).collect(Collectors.toSet());
    }

}
