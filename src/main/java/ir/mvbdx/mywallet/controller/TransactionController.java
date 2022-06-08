package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Transaction;
import ir.mvbdx.mywallet.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(value = {"/api/transaction", "/api/transactions"})
public class TransactionController extends BaseController<Transaction> {

    public TransactionController(@Qualifier("transactionServiceImpl") BaseService<Transaction> baseService) {
        super(baseService);
    }

}