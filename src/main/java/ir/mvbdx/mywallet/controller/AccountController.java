package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Account;
import ir.mvbdx.mywallet.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/account", "/api/accounts"})
public class AccountController extends BaseController<Account> {

    public AccountController(@Qualifier("accountServiceImpl") BaseService<Account> baseService) {
        super(baseService);
    }

}