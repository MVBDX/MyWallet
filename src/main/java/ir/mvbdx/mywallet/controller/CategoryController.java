package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.entity.Category;
import ir.mvbdx.mywallet.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("categoryForm", new Category());
        model.addAttribute("categories", categoryService.findAll());
        return "category/add-category";
    }

    @GetMapping({"/list"})
    public ModelAndView listAll() {
        ModelAndView mav = new ModelAndView("category/list-category");
        mav.addObject("categories", categoryService.findAll());
        return mav;
    }

    @PostMapping("/save")
    public String saveForm(@ModelAttribute("categoryForm") Category category) {
        categoryService.save(category);
        return "redirect:/category/list";
    }

    @PutMapping("/edit/save")
    public String update(@ModelAttribute("categoryForm") Category category) {
        categoryService.update(category.getId(), category);
        return "redirect:/category/list";
    }

    @GetMapping("/edit/{id}")
    public String editById(Model model, @PathVariable("id") Long id) {
        model.addAttribute("categoryForm", categoryService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "category/add-category";
    }

    @GetMapping("/delete/{id}")
//    @ResponseStatus(HttpStatus.OK) : is for rest
    public String delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
        return "redirect:/category/list";
    }

}