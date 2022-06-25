package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.entity.TransactionType;
import ir.mvbdx.mywallet.service.impl.AccountServiceImpl;
import ir.mvbdx.mywallet.service.impl.CategoryServiceImpl;
import ir.mvbdx.mywallet.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceImpl service;
    private final CategoryServiceImpl categoryService;
    private final AccountServiceImpl accountService;

    @GetMapping("/new")
    public String newForm(Model model) {
        List<Category> categoryList = categoryService.findAll();
        List<Account> accountList = accountService.findAll();
        model.addAttribute("transactionForm", new Transaction());
        model.addAttribute("accounts", accountList);
        model.addAttribute("categories", categoryList);
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("types",
                Stream.of(TransactionType.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));
        return "transaction/add-transaction";
    }

    @GetMapping({"/list", "/"})
    public ModelAndView listAll() {
        ModelAndView mav = new ModelAndView("transaction/list-transaction");
        mav.addObject("transactions", service.findAll());
        mav.addObject("totalIncome", service.totalIncome());
        mav.addObject("totalSpend", service.totalSpend());
        mav.addObject("totalBalance", service.totalBalance());
        return mav;
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute("transactionForm") Transaction transaction) {
        service.save(transaction);
        return "redirect:/transaction/list";
    }

    @PutMapping("/edit/save")
    public String update(@ModelAttribute("transactionForm") Transaction transaction) {
        service.update(transaction.getId(), transaction);
        return "redirect:/transaction/list";
    }

    @GetMapping("/edit/{id}")
    public String editById(Model model, @PathVariable("id") Long id) {
        List<Category> categoryList = categoryService.findAll();
        List<Account> accountList = accountService.findAll();
        model.addAttribute("transactionForm", service.findById(id));
        model.addAttribute("accounts", accountList);
        model.addAttribute("categories", categoryList);
        model.addAttribute("currentDate", LocalDate.now());
        model.addAttribute("types",
                Stream.of(TransactionType.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));
        return "transaction/add-transaction";
    }

    @GetMapping("/delete/{id}")
//    @ResponseStatus(HttpStatus.OK) : is for rest
    public String delete(@PathVariable("id") Long id) {
        service.delete(id);
        return "redirect:/transaction/list";
    }


}