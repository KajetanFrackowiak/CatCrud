package com.example.demo.Selenium;


import com.example.demo.service.MyRestService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


class SeleniumAddCatPageTest {
    WebDriver driver;
    MyRestService service;

    @FindBy(id = "name")
    WebElement nameInput;

    @FindBy(id = "age")
    WebElement ageInput;

    @FindBy(id = "send")
    WebElement sendSubmit;

    public static final String  URL ="http://localhost:8080/addCat";
    public static final String  URLALL ="http://localhost:8080/allCat";

    public SeleniumAddCatPageTest(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void open(){
        driver.get(URL);
        driver.get(URLALL);
    }

    public void fillInInput(){
        nameInput.sendKeys("Eryk");
        ageInput.clear();
        ageInput.sendKeys("5");
    }

    public void sendSubmit(){
        sendSubmit.click();
    }

    public void checkThatCatExist(){
        service.getCatByName("Eryk");

    }

}
