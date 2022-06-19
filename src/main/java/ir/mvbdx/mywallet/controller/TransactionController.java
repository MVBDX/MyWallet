package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.entity.TransactionType;
import ir.mvbdx.mywallet.service.BaseService;
import ir.mvbdx.mywallet.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = {"/api/transaction", "/transaction"})
public class TransactionController extends BaseController<Transaction> {

    @Autowired
    @Qualifier("categoryServiceImpl")
    private BaseService<Category> categoryService;

    @Autowired
    @Qualifier("accountServiceImpl")
    private BaseService<Account> accountService;

    public TransactionController(@Qualifier("transactionServiceImpl") BaseService<Transaction> baseService) {
        super(baseService);
    }

    @GetMapping("/new")
    public String showNewTransactionForm(Model model) {
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
    public ModelAndView getAllTransaction() {
        ModelAndView mav = new ModelAndView("transaction/list-transaction");
        mav.addObject("transactions", super.baseService.findAll());
        TransactionServiceImpl downCastedTransactionService = (TransactionServiceImpl) baseService;
        mav.addObject("totalIncome", downCastedTransactionService.totalIncome());
        mav.addObject("totalSpend", downCastedTransactionService.totalSpend());
        mav.addObject("totalBalance", downCastedTransactionService.totalBalance());
        return mav;
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("transactionForm") Transaction transaction) {
        baseService.save(transaction);
        return "redirect:/api/transaction/list";
    }

}