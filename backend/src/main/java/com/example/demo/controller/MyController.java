//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.Cat;
import com.example.demo.service.MyRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    private final MyRestService myRestService;

    @Autowired
    public MyController(MyRestService myRestService) {
        this.myRestService = myRestService;
    }

    @GetMapping({"/cat/{name}"})
    public Optional<Cat> getByName(@PathVariable("name") String name) {
        return this.myRestService.getCatByName(name);
    }

    @GetMapping({"/cats"})
    public ArrayList<Cat> getAll() {
        return this.myRestService.getAllCats();
    }

    @PostMapping({"/cat/add"})
    public Optional<Cat> postCat(@RequestBody Cat cat) {
        return this.myRestService.addCat(cat);
    }

    @DeleteMapping({"/cat/delete/{name}"})
    public void deleteByName(@PathVariable("name") String name) {
        this.myRestService.deleteCatByName(name);
    }

    @PutMapping({"/cat/update/{name}"})
    public Optional<Cat> updateByName(@PathVariable("name") String name, @RequestBody Cat cat) {
        return this.myRestService.updateCatByName(name, cat);
    }

    @GetMapping({"/cats/{name}"})
    public List<Cat> getAllCatThatContainsName(@PathVariable("name") String name) {
        return this.myRestService.findCatsThatNameIsContainsNameFromLink(name);
    }
}
