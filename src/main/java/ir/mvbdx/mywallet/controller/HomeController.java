package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.dto.CustomerDTO;
import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.entity.Customer;
import ir.mvbdx.mywallet.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {
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
        CustomerDTO customerDTO = new CustomerDTO();
        model.addAttribute("customerDTO", customerDTO);
        return "register";
    }

    @PostMapping("/result")
    public String submissionResult(@ModelAttribute("customerDTO") Customer customer) {
        customerService.save(customer);
        return "redirect:/login";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Category category) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("category-data");
        modelAndView.addObject("category", category);
        return modelAndView;
    }
}
