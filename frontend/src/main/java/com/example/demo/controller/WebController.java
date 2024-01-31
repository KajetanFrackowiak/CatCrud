package com.example.demo.controller;

import com.example.demo.model.Cat;
import com.example.demo.service.MyRestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    public final MyRestService service;

    public WebController(MyRestService service) {
        this.service = service;
    }
    @GetMapping("/index")
    public String getWelcomeView(){
        return "index";
    }
    @GetMapping(value = "/allCat")
    public String getViewAll (Model model){
        model.addAttribute("allCats",service.getAllCats());
        return "allCat";
    }

    @GetMapping(value = "/addCat")
    public String addCat(Model model){
        model.addAttribute("cat", new Cat("",0));
        model.addAttribute("allCats", service.getAllCats());
        return "addCat";
    }

    @PostMapping(value = "/addCat")
    public String addCat(@ModelAttribute Cat cat){
        if (cat.getAge()<=0){
            return "addCat";
        }
        service.addCat(cat);
        return "redirect:/allCat";
    }

    @GetMapping(value = "/updateCat/{name}")
    public String updateCat(Model model, @PathVariable("name") String name){
        Cat cat = service.getCatByName(name);
        model.addAttribute("cat", service.updateCatName(name,cat));
        model.addAttribute("allCats", service.getAllCats());
        return "updateCat";
    }
    @PostMapping(value = "/updateCat")
    public String updateCat(@ModelAttribute Cat cat){
        service.updateCatName(cat.getName(),cat);
        return "redirect:/allCat";
    }

    @GetMapping(value = "/deleteCat/{name}")
    public String deleteCat(@PathVariable("name") String name){
        service.deleteCatByName(name);
        return "redirect:/allCat";
    }
}
