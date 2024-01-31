package com.example.demo.controller;

import com.example.demo.Cat;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.when;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyControllerIntegrityTest {
    private static final String URI = "http://localhost:8080";
    @Test
    public void findCatByNameWhenCatExist(){
        when()
                .get(URI + "/cat/Dan")
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(3))
                .body("name", equalTo("Dan"))
                .body("age", equalTo(12))
                .log()
                .body();
    }
    @Test
    public void findCatByNameWhenCatIsNotExist(){
        when()
                .get(URI + "/cat/fsfds")
                .then()
                .statusCode(404)
                .assertThat()
                .log()
                .body();
    }

    @Test
    public void findAllCat(){
        List<Cat> list = List.of(when()
                .get(URI + "/cats")
                .then()
                .statusCode(200)
                .extract()
                .as(Cat[].class));

        assertEquals(2,list.get(0).getAge());
    }

    @Test
    public void addCatWhenCatIsNotExist(){
        with()
                .body(new Cat("Kate", 3))
                .contentType("application/json")
                .post(URI + "/cat/add")
                .then()
                .statusCode(200)
                .body("name", equalTo("Kate"))
                .body("age", equalTo(3))
                .log()
                .body();
    }
    @Test
    public void addCatWhenCatExist(){
        with()
                .body(new Cat("George", 3))
                .contentType("application/json")
                .post(URI + "/cat/add")
                .then()
                .statusCode(400)
                .log()
                .body();
    }

    @Test
    public void deleteCatWhenCatExist(){
        with()
                .delete(URI + "/cat/delete/DanBob")
                .then()
                .statusCode(200)
                .log()
                .body();
    }
    @Test
    public void deleteCatWhenCatIsNotExist(){
        with()
                .delete(URI + "/cat/delete/gfdsgfs")
                .then()
                .statusCode(404)
                .log()
                .body();
    }

    @Test
    public void updateCatWhenCataExist(){
        with()
                .body(new Cat("Kate", 4))
                .contentType("application/json")
                .put(URI + "/cat/update/Kate")
                .then()
                .statusCode(200)
                .body("name", equalTo("Kate"))
                .body("age", equalTo(4))
                .log()
                .body();
    }
    @Test
    public void updateCatWhenCatIsNotExist(){
        with()
                .body(new Cat("fdsfds", 4))
                .contentType("application/json")
                .put(URI + "/cat/update/fdsfds")
                .then()
                .statusCode(404)
                .log()
                .body();
    }
}
