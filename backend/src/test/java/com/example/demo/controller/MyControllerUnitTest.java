package com.example.demo.controller;


import com.example.demo.Cat;
import com.example.demo.controller.MyController;
import com.example.demo.exception.CatExceptionHandler;
import com.example.demo.exception.exceptionsClass.CatAgeIsToLowException;
import com.example.demo.exception.exceptionsClass.CatAlreadyExistException;
import com.example.demo.exception.exceptionsClass.CatNotExistException;
import com.example.demo.service.MyRestService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MyControllerUnitTest {
    @Mock
    private MyRestService service;
    @InjectMocks
    private MyController controller;
    private MockMvc mockMvc;
    private AutoCloseable openMocks;

    @BeforeEach
    public void init() {
        openMocks = MockitoAnnotations.openMocks(this);
        controller = new MyController(service);
    }

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(
                new CatExceptionHandler(), controller
        ).build();
    }

    @AfterEach
    public void closeDownTest() throws Exception {
        openMocks.close();
    }

    @Test
    public void searchCatByNameWhenCatExist() throws Exception {
        when(service.getCatByName("Bob")).thenReturn(Optional.of(new Cat("Bob", 2)));

        mockMvc.perform(get("/cat/Bob"))
                .andExpect(jsonPath("$.age").value("2"))
                .andExpect(jsonPath("$.name").value("Bob"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchCatByNameWhenCatIsNotExist() throws Exception {
        when(service.getCatByName("Ala")).thenThrow(new CatNotExistException());

        mockMvc.perform(get("/Cat/Ala"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addCatWhenCatIsNotExist() throws Exception {
        var cat = new Cat("Bob", 2);

        when(service.addCat(any())).thenReturn(Optional.of(cat));

        mockMvc.perform(post("/cat/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Bob\", \"age\": \"2\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addCatWhenCatExist() throws Exception {
        when(service.addCat(any())).thenThrow(new CatAlreadyExistException());

        mockMvc.perform(post("/cat/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Bob\", \"age\": \"2\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getAllCatWhenCatExist() throws Exception {
        var catList = new ArrayList<Cat>();
        catList.add(new Cat("Maciek", 12));
        catList.add(new Cat("Monika", 7));

        when(service.getAllCats()).thenReturn(catList);

        mockMvc.perform(get("/cats"))
                .andExpect(jsonPath("$.[0].age").value("12"))
                .andExpect(jsonPath("$.[0].name").value("Maciek"))
                .andExpect(jsonPath("$.[1].age").value("7"))
                .andExpect(jsonPath("$.[1].name").value("Monika"))
                // Poczytac jest odwołanie do konkretnego elemntu w tablicy
                .andExpect(status().isOk());
    }

    @Test
    public void getAllCatWhenCatIsNotExist() throws Exception {

        when(service.getAllCats()).thenThrow(new CatNotExistException());

        mockMvc.perform(get("/cats"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteCatByNameWhenCatExist() throws Exception {
        mockMvc.perform(delete("/cat/delete/Maciek"))
                .andExpect(status().isOk());

        verify(service).deleteCatByName("Maciek");
    }

    @Test
    public void deleteCatByNameWhenCatIsNotExist() throws Exception {
        doThrow(CatNotExistException.class).when(service).deleteCatByName(any());

        mockMvc.perform(delete("/cat/delete/Maciek"))
                .andExpect(status().isNotFound());

//        verify(service, times(0)).deleteCatByName("Maciek");
    }

    @Test
    public void updateCatByNameWhenCatExist() throws Exception {
        var cat = new Cat("Marcel", 4);
        when(service.updateCatByName(eq(cat.getName()), any())).thenReturn(Optional.of(cat));

        mockMvc.perform(put("/cat/update/Marcel")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Marcel\", \"age\": \"5\"}")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void updateCatByNameWhenCatIsNotExist() throws Exception {
        var cat = new Cat("Marcel", 4);
        when(service.updateCatByName(eq(cat.getName()), any())).thenThrow(new CatNotExistException());

        mockMvc.perform(put("/cat/update/Marcel")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"age\": \"5\"}")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());
    }

    @Test
    public void updateCatByNameWhenCatAgeIsToLow() throws Exception {
        var cat = new Cat("Marcel", 4);
        when(service.updateCatByName(eq(cat.getName()), any()))
                .thenThrow(new CatAgeIsToLowException());

        mockMvc.perform(put("/cat/update/Marcel")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"name\": \"Marcel\", \"age\": \"2\"}")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }
}

