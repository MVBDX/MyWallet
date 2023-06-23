package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.model.entity.Account;
import ir.mvbdx.mywallet.service.AccountService;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final CustomerService customerService;

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("accountForm", new Account());
        return "account/add-account";
    }

    @GetMapping({"/list", "/"})
    public ModelAndView listAll(Principal principal) {
        var mav = new ModelAndView("account/list-account");
        mav.addObject("accounts", customerService.accountsOfCustomer(principal));
        mav.addObject("totalBalance", accountService.totalBalance(principal));
        mav.addObject("totalBalanceWithoutCredits", accountService.totalBalanceWithoutCredits(principal));
        return mav;
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute("accountForm") Account account, Principal principal) {
        account.setCustomer(customerService.findCustomer(principal));
        accountService.save(account);
        return "redirect:/account/list";
    }

    @PutMapping("/edit/save")
    public String update(@ModelAttribute("accountForm") Account account, Principal principal) {
        account.setCustomer(customerService.findCustomer(principal));
        accountService.update(account.getId(), account);
        return "redirect:/account/list";
    }

    @GetMapping("/edit/{id}")
    public String editById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("accountForm", accountService.findById(id));
        return "account/add-account";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        accountService.delete(id);
        return "redirect:/account/list";
    }

}