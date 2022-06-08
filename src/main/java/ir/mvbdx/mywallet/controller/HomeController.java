package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Category;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/save")
    public ModelAndView save(@ModelAttribute Category category)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("category-data");
        modelAndView.addObject("category", category);
        return modelAndView;
    }
}
