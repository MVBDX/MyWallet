package ir.mvbdx.mywallet.controller;

import ir.mvbdx.mywallet.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public abstract class BaseController<T> {

    private final BaseService<T> baseService;

    protected BaseController(BaseService<T> baseService) {
        this.baseService = baseService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public T save(@RequestBody T base) {
        return baseService.save(base);
    }

    @GetMapping("/{id}")
    public T findById(@PathVariable("id") Long id) {
        return baseService.findById(id);
    }

    @GetMapping("/")
    public List<T> findAll() {
        return baseService.findAll();
    }

    @PutMapping("/edit/{id}")
    public T update(@PathVariable("id") Long id, @RequestBody T base) {
        return baseService.update(id, base);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable("id") Long id) {
        return baseService.delete(id);
    }

}
