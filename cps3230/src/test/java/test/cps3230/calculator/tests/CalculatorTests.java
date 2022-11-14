package test.cps3230.calculator.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import test.cps3230.calculator.pageobjects.CalculatorPageObject;

import java.util.List;

public class CalculatorTests {

    WebDriver webdriver;
    CalculatorPageObject calc;

    //setup method
    @BeforeEach
    public void setup() throws InterruptedException {
        webdriver = new SafariDriver();
        //go to calculator
        webdriver.get("http://www.math.com/students/calculators/source/basic.htm");
        calc = new CalculatorPageObject(webdriver);
        Thread.sleep(5000);
    }

    //teardown method
    @AfterEach
    public void teardown(){
        webdriver.quit();
    }

    @Test
    public void additionTest(){
        //Exercise
        calc.inputExpression("5+2=");

        //Verify
        Assertions.assertEquals("7", calc.getResult());
    }


}
