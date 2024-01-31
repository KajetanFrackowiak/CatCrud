package com.example.demo.service;

import com.example.demo.Cat;
import com.example.demo.exception.exceptionsClass.CatAgeIsToLowException;
import com.example.demo.exception.exceptionsClass.CatAlreadyExistException;
import com.example.demo.exception.exceptionsClass.CatNotExistException;
import com.example.demo.repository.CatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyRestServiceUnitTest {
    @Mock
    private CatRepository repository;
    @InjectMocks
    private MyRestService service;
    private AutoCloseable openMocks;

    @BeforeEach
    public void init(){
        openMocks = MockitoAnnotations.openMocks(this);
        service = new MyRestService(repository);
    }

    @AfterEach
    public void closeDownTest() throws Exception{
        openMocks.close();
    }

    @Test
    public void findCatByNameWhenCatExist(){
        Cat cat = new Cat("Adam", 3);
        when(repository.findByName(cat.getName())).thenReturn(Optional.of(cat));
        var result = service.getCatByName(cat.getName());
        assertEquals(result.get(),cat);
    }
    @Test
    public void findCatByNameWhenCatIsNotExist(){
        when(repository.findByName("Adam")).thenReturn(empty());

        assertThrows(CatNotExistException.class, () -> service.getCatByName("Adam"));
    }

    @Test
    public void addCatWhenCatIsNotExist(){
        Cat cat = new Cat("Adam", 3);

        when(repository.findByName(cat.getName())).thenReturn(empty());
        when(repository.save(cat)).thenReturn(cat);

        var result = service.addCat(cat);

        assertEquals(cat, result.get());
    }
    @Test
    public void addCatWhenCatExist(){
        Cat cat = new Cat("Adam", 4);
        when(repository.findByName(cat.getName())).thenReturn(Optional.of(cat));

        assertThrows(CatAlreadyExistException.class, () -> service.addCat(cat));
    }

    @Test
    public void getAllCatWhenCatExist(){
        var capyList = new ArrayList<Cat>();
        capyList.add(new Cat("Marek",1));
        capyList.add(new Cat("Adam",5));

        when(repository.findAll()).thenReturn(capyList);

        var result = service.getAllCats();
        assertEquals(capyList, result);
    }
    @Test
    public void getAllCatWhenCatIsNotExist(){
        var capyList = new ArrayList<Cat>();

        when(repository.findAll()).thenReturn(capyList);

        assertThrows(CatNotExistException.class, () -> service.getAllCats());
    }

    @Test
    public void deleteCatByNameWhenCatExist(){
        Cat cat = new Cat("Marek",12);

        when(repository.findByName(cat.getName())).thenReturn(Optional.of(cat));

        service.deleteCatByName(cat.getName());

        verify(repository).delete(cat);
    }
    @Test
    public void deleteCatByNameWhenCatIsNotExist(){
        Cat cat = new Cat("Adam", 4);
        when(repository.findByName(cat.getName())).thenReturn(empty());

        assertThrows(CatNotExistException.class, () -> service.deleteCatByName(cat.getName()));
    }

    @Test
    public void updateCatByNameWhenCatExist(){
        Cat cat = new Cat("Marta",6);
        Cat catUpdate = new Cat("Marta",12);

        when(repository.findByName(cat.getName())).thenReturn(Optional.of(cat));
        when(repository.save(cat)).thenReturn(catUpdate);

        var result = service.updateCatByName(cat.getName(),catUpdate);


        assertEquals(catUpdate, result.get());
    }
    @Test
    public void updateCatByNameWhenCatIsNotExist(){
        Cat cat = new Cat("Adam", 4);
        when(repository.findByName(cat.getName())).thenReturn(empty());

        assertThrows(CatNotExistException.class, () -> service.updateCatByName(cat.getName(),cat));
    }

    @Test
    public void updateCatByNameWhenCatAgeIsToLow(){
        Cat cat = new Cat("Adam", 4);
        Cat catUpdate = new Cat("Adam", 2);
        when(repository.findByName(cat.getName())).thenReturn(Optional.of(cat));

        assertThrows(CatAgeIsToLowException.class, () -> service.updateCatByName(cat.getName(),catUpdate));
    }
    @Test
    public void updateCatByNameWhenCatAgeIsOk(){
        Cat cat = new Cat("Adam", 4);
        Cat catUpdate = new Cat("Adam", 5);
        when(repository.findByName(cat.getName())).thenReturn(Optional.of(cat));
        when(repository.save(cat)).thenReturn(catUpdate);

        var result = service.updateCatByName("Adam",catUpdate);

        assertEquals(result.get(), catUpdate);
    }

    @Test
    public void findCatsThatNameIsContainsNameFromLinkWhenCatIsExist(){
        var capyList = new ArrayList<Cat>();
        var capyListResult = new ArrayList<Cat>();
        Cat cat1 = new Cat("Marek",1);
        Cat cat2 = new Cat("Marek2",5);
        Cat cat3 = new Cat("Mark",5);
        capyList.add(cat1);
        capyList.add(cat2);
        capyList.add(cat3);
        capyListResult.add(cat1);
        capyListResult.add(cat2);

        when(repository.findAll()).thenReturn(capyList);

        var result = service.findCatsThatNameIsContainsNameFromLink("Marek");
        assertEquals(capyListResult, result);
    }
}
