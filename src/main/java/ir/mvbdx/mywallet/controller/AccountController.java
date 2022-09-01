package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.*;
import ir.mvbdx.mywallet.enumeration.AccountType;
import ir.mvbdx.mywallet.service.impl.AccountServiceImpl;
import ir.mvbdx.mywallet.service.impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountServiceImpl accountService;
    private final CustomerServiceImpl customerService;

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("accountForm", new Account());
        model.addAttribute("types",
                Stream.of(AccountType.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));
        return "account/add-account";
    }

    @GetMapping({"/list", "/"})
    public ModelAndView listAll(Principal principal) {
        ModelAndView mav = new ModelAndView("account/list-account");
        mav.addObject("accounts", customerService.getAllAccounts(principal));
        mav.addObject("totalBalance", accountService.totalBalance(principal));
        return mav;
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute("accountForm") Account account, Principal principal) {
        account.setCustomer(customerService.findLoggedInUser(principal));
        accountService.save(account);
        return "redirect:/account/list";
    }

    @PutMapping("/edit/save")
    public String update(@ModelAttribute("accountForm") Account account, Principal principal) {
        account.setCustomer(customerService.findLoggedInUser(principal));
        accountService.update(account.getId(), account);
        return "redirect:/account/list";
    }

    @GetMapping("/edit/{id}")
    public String editById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("accountForm", accountService.findById(id));
        model.addAttribute("types",
                Stream.of(AccountType.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));
        return "account/add-account";
    }

    @GetMapping("/delete/{id}")
//    @ResponseStatus(HttpStatus.OK) : is for rest
    public String delete(@PathVariable("id") Long id) {
        accountService.delete(id);
        return "redirect:/account/list";
    }

}