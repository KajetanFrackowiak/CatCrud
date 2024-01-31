//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.demo.service;

import com.example.demo.Cat;
import com.example.demo.exception.exceptionsClass.CatAgeIsToLowException;
import com.example.demo.exception.exceptionsClass.CatAlreadyExistException;
import com.example.demo.exception.exceptionsClass.CatNotExistException;
import com.example.demo.repository.CatRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class MyRestService {
    private final CatRepository repository;

    public MyRestService(CatRepository repository) {
        this.repository = repository;
        this.repository.save(new Cat("Mateusz", 2));
        this.repository.save(new Cat("Eryk", 5));
        this.repository.save(new Cat("Stefan", 12));
        this.repository.save(new Cat("Bob", 6));
        this.repository.save(new Cat("Alicja", 7));
    }

    public Optional<Cat> getCatByName(String name) {
        if (this.repository.findByName(name).isPresent()) {
            return this.repository.findByName(name);
        } else {
            throw new CatNotExistException();
        }
    }

    public ArrayList<Cat> getAllCats() {
        if (!((ArrayList)this.repository.findAll()).isEmpty()) {
            return (ArrayList)this.repository.findAll();
        } else {
            throw new CatNotExistException();
        }
    }

    public Optional<Cat> addCat(Cat cat) {
        if (this.repository.findByName(cat.getName()).isEmpty()) {
            return Optional.of((Cat)this.repository.save(cat));
        } else {
            throw new CatAlreadyExistException();
        }
    }

    public void deleteCatByName(String name) {
        Optional<Cat> cat = this.repository.findByName(name);
        if (cat.isPresent()) {
            this.repository.delete((Cat)cat.get());
        } else {
            throw new CatNotExistException();
        }
    }

    public Optional<Cat> updateCatByName(String name, Cat cat1) {
        Optional<Cat> cat = this.repository.findByName(name);
        if (cat.isPresent()) {
            if (((Cat)cat.get()).getAge() <= cat1.getAge()) {
                ((Cat)cat.get()).setAge(cat1.getAge());
                return Optional.of((Cat)this.repository.save((Cat)cat.get()));
            } else {
                throw new CatAgeIsToLowException();
            }
        } else {
            throw new CatNotExistException();
        }
    }

    public List<Cat> findCatsThatNameIsContainsNameFromLink(String name) {
        ArrayList<Cat> catListWithAllCat = (ArrayList)this.repository.findAll();
        return catListWithAllCat.stream().filter((cat) -> {
            return cat.getName().contains(name);
        }).toList();
    }
}
