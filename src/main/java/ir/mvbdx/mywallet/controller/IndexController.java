package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.dto.CustomerDTO;
import ir.mvbdx.mywallet.entity.Customer;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final CustomerService customerService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("customerDTO", new CustomerDTO());
        return "register";
    }

    @PostMapping("/result")
    public String submitRegistration(@ModelAttribute("customerDTO") Customer customer, Model model) {
        customerService.save(customer);
        model.addAttribute("customerDTO", customer);
        return "result";
    }

}