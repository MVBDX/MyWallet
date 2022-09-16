package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.enumeration.TransactionType;
import ir.mvbdx.mywallet.service.impl.AccountServiceImpl;
import ir.mvbdx.mywallet.service.impl.CategoryServiceImpl;
import ir.mvbdx.mywallet.service.impl.CustomerServiceImpl;
import ir.mvbdx.mywallet.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceImpl transactionService;
    private final CategoryServiceImpl categoryService;
    private final AccountServiceImpl accountService;
    private final CustomerServiceImpl customerService;

    @GetMapping("/new")
    public String newForm(Model model, Principal principal) {
        List<Category> categoryList = categoryService.findAll();
        List<Account> accountList = customerService.getAllAccounts(principal);
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
    public ModelAndView listAll(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize, Principal principal) {
        ModelAndView mav = new ModelAndView("transaction/list-transaction");
        mav.addObject("transactions", transactionService.findAllByCustomerOrderByDate(pageNumber, pageSize, principal));
        mav.addObject("totalIncome", transactionService.totalIncome());
        mav.addObject("totalSpend", transactionService.totalSpend());
        mav.addObject("totalBalance", transactionService.totalBalance());
        mav.addObject("totalAccountsBalance", accountService.totalBalance(principal));
        return mav;
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute("transactionForm") Transaction transaction) {
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
//    @ResponseStatus(HttpStatus.OK) : is for rest
    public String delete(@PathVariable("id") Long id) {
        transactionService.delete(id);
        return "redirect:/transaction/list";
    }


}