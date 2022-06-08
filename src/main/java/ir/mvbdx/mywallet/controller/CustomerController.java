package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Customer;
import ir.mvbdx.mywallet.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(value = {"/api/customer", "/api/customers"})
public class CustomerController extends BaseController<Customer> {

    public CustomerController(@Qualifier("customerServiceImpl") BaseService<Customer> baseService) {
        super(baseService);
    }

}