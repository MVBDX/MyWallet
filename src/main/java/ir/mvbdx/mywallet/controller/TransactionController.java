package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.model.entity.Account;
import ir.mvbdx.mywallet.model.entity.Category;
import ir.mvbdx.mywallet.model.entity.Transaction;
import ir.mvbdx.mywallet.model.entity.paging.Paged;
import ir.mvbdx.mywallet.model.entity.paging.Paging;
import ir.mvbdx.mywallet.model.enums.TransactionType;
import ir.mvbdx.mywallet.service.AccountService;
import ir.mvbdx.mywallet.service.CategoryService;
import ir.mvbdx.mywallet.service.CustomerService;
import ir.mvbdx.mywallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final CustomerService customerService;

    @GetMapping({"/list", "/"})
    public ModelAndView listAll(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize, Principal principal) {
        var mav = new ModelAndView("transaction/list-transaction");
        Page<Transaction> transactions = transactionService.findAllByCustomerOrderByDate(pageNumber, pageSize, principal);
        mav.addObject("transactions", new Paged<>(transactions, Paging.of(transactions.getTotalPages(), pageNumber, pageSize)));
        mav.addObject("totalIncome", transactionService.totalIncome(principal));
        mav.addObject("totalSpend", transactionService.totalSpend(principal));
        mav.addObject("totalBalance", transactionService.totalBalance(principal));
        mav.addObject("totalAccountsBalance", accountService.totalBalance(principal));
        return mav;
    }

    @GetMapping("/new")
    public String newForm(Model model, Principal principal) {
        List<Category> categoryList = categoryService.findAll();
        List<Account> accountList = customerService.accountsOfCustomer(principal);
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

    @PostMapping("/save")
    public String saveForm(@Valid @ModelAttribute("transactionForm") Transaction transaction, BindingResult result) {
        if (result.hasErrors())
            return "transaction/add-transaction";

        transactionService.save(transaction);
        return "redirect:/transaction/list";
    }

    @PutMapping("/edit/save")
    public String update(@ModelAttribute("transactionForm") Transaction transaction) {
        transactionService.update(transaction.getId(), transaction);
        return "redirect:/transaction/list";
    }

    @GetMapping("/edit/{id}")
    public String editById(Model model, @PathVariable("id") Long id) {
        List<Category> categoryList = categoryService.findAll();
        List<Account> accountList = accountService.findAll();
        model.addAttribute("transactionForm", transactionService.findById(id));
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
    public String delete(@PathVariable("id") Long id) {
        transactionService.delete(id);
        return "redirect:/transaction/list";
    }

}