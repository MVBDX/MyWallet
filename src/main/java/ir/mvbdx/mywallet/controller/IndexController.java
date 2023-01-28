package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.model.dto.CustomerDTO;
import ir.mvbdx.mywallet.model.entity.Customer;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

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
    public String submitRegistration(@Valid @ModelAttribute("customerDTO") Customer customer, BindingResult result, Model model) {
        if (result.hasErrors())
            return "register";

        customerService.save(customer);
        model.addAttribute("customerDTO", customer);
        return "result";
    }

}
