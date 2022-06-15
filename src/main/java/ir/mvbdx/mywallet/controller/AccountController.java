package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.*;
import ir.mvbdx.mywallet.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = {"/api/account", "/account"})
public class AccountController extends BaseController<Account> {

    public AccountController(@Qualifier("accountServiceImpl") BaseService<Account> baseService) {
        super(baseService);
    }

    @GetMapping("/new")
    public String showNewTransactionForm(Model model) {
        model.addAttribute("accountForm", new Account());
        model.addAttribute("types",
                Stream.of(AccountType.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));
        return "account/add-account";
    }

    @GetMapping({"/list", "/"})
    public ModelAndView getAllAccount() {
        ModelAndView mav = new ModelAndView("account/list-account");
        mav.addObject("accounts", super.baseService.findAll());
        return mav;
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("accountForm") Account account) {
        baseService.save(account);
        return "redirect:/api/account/list";
    }

}