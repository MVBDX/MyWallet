package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.dto.CustomerDTO;
import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.entity.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

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
        CustomerDTO userDto = new CustomerDTO();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/result")
    public String submissionResult(@ModelAttribute("customerForm") Customer customer) {
        return "result";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Category category) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("category-data");
        modelAndView.addObject("category", category);
        return modelAndView;
    }
}
