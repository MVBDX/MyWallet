package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.service.BaseService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/api/category", "/category"})
public class CategoryController extends BaseController<Category> {

    public CategoryController(@Qualifier("categoryServiceImpl") BaseService<Category> baseService) {
        super(baseService);
    }

    @GetMapping("/new")
    public String newCategory(Model model) {
        model.addAttribute("categoryForm", new Category());
        model.addAttribute("categories", baseService.findAll());
        return "category/add-category";
    }

    @PostMapping("/save")
    public String saveCategory(@ModelAttribute("categoryForm") Category category) {
        baseService.save(category);
        return "redirect:/api/category/list";
    }


    @GetMapping({"/list"})
    public ModelAndView getAllCategory() {
        ModelAndView mav = new ModelAndView("category/list-category");
        mav.addObject("categories", super.baseService.findAll());
        return mav;
    }

}