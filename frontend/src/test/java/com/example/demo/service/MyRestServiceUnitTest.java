package com.example.demo.service;


import com.example.demo.model.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.web.client.MockServerRestClientCustomizer;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestClient;

import static com.example.demo.service.MyRestService.BASE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RestClientTest
public class MyRestServiceUnitTest {
    @InjectMocks
    private MyRestService service;
    private MockServerRestClientCustomizer customizer = new MockServerRestClientCustomizer();
    private RestClient.Builder builder = RestClient.builder();

    @BeforeEach
    public void setUp() {
        customizer.customize(builder);
        service = new MyRestService(builder.build());
    }

    @Test
    public void findCatByNameWhenCatExist() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/cat/Eryk"))
                .andRespond(MockRestResponseCreators
                        .withSuccess(""" 
                                {"name":"Eryk", "age":5}
                                """, MediaType.APPLICATION_JSON));
        Cat cat = service.getCatByName("Eryk");
        assertEquals("Eryk", cat.getName());
    }

    @Test
    public void findAllCatWhenCatExist() {
        customizer.getServer().expect(MockRestRequestMatchers.requestTo(
                        BASE_URL + "/cats"))
                .andRespond(MockRestResponseCreators
                        .withSuccess(""" 
                                [{"name":"Eryk", "age":5}]
                                """, MediaType.APPLICATION_JSON));
        var list = service.getAllCats();
        assertEquals("Eryk", list.get(0).getName());
        assertEquals(5, list.get(0).getAge());
    }
}
