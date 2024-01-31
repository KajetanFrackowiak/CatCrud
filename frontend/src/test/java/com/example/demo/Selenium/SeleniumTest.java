package com.example.demo.Selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class SeleniumTest {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new EdgeDriver();
    }

    @Test
    public void fillInForm() {
        SeleniumAddCatPageTest page = new SeleniumAddCatPageTest(driver);
        page.open();
        page.fillInInput();
        page.sendSubmit();
        page.checkThatCatExist();
    }
}
