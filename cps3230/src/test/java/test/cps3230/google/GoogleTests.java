package test.cps3230.google;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import java.util.List;

public class GoogleTests {

    WebDriver webdriver;

    //setup method
    @BeforeEach
    public void setup() throws InterruptedException {
        webdriver = new SafariDriver();

        //go to google and disable cookies
        webdriver.get("https://www.google.com");

        WebElement acceptCookiesButton = webdriver.findElement(By.id("L2AGLb"));
        acceptCookiesButton.click();
        /*
        Thread.sleep(2000);
        webdriver.switchTo().activeElement().sendKeys(Keys.TAB);
        Thread.sleep(2000);
        webdriver.switchTo().activeElement().sendKeys(Keys.TAB);
        Thread.sleep(2000);
        webdriver.switchTo().activeElement().sendKeys(Keys.TAB);
        Thread.sleep(2000);
        webdriver.switchTo().activeElement().sendKeys(Keys.TAB);
        Thread.sleep(2000);
        webdriver.switchTo().activeElement().sendKeys(Keys.TAB);
        Thread.sleep(2000);
        webdriver.switchTo().activeElement().sendKeys(Keys.RETURN);
        Thread.sleep(2000);
         */
    }

    //teardown method
    @AfterEach
    public void teardown() {
        webdriver.quit();
    }


    @Test
    public void testSimpleGoogleSearch() throws InterruptedException {
        //Exercise

        //find searchfield
        WebElement searchField = webdriver.findElement(By.name("q"));
        //enter malta in serachfield
        searchField.sendKeys("Malta");
        Thread.sleep(1000);

        //find searchbutton
        WebElement searchButton = webdriver.findElement(By.name("btnK"));
        //submit searchbutton
        searchButton.submit();
        Thread.sleep(1000);

        //Verify
        String title = webdriver.getTitle();
        Assertions.assertEquals("Malta - Google Search", title);

        //Check that resultStats component exists
        //This returns all elements that have result-stats
        List<WebElement> resultStats = webdriver.findElements(By.id("result-stats"));
        Assertions.assertEquals(1, resultStats.size());

        //Check map of Malta size
        List<WebElement> mapOfMalta = webdriver.findElements(By.id("lu_map"));
        Assertions.assertEquals(1, mapOfMalta.size());

        //Check map of malta exists
        String mapOfMaltaTitle = mapOfMalta.get(0).getAttribute("title");
        Assertions.assertEquals("Map of Malta", mapOfMaltaTitle);

        //xpath test
        String xPathToMapOfMalta = "//img[@id = 'lu_map' and @title = 'Map of Malta']";
        mapOfMalta = webdriver.findElements(By.xpath(xPathToMapOfMalta));
        Assertions.assertEquals(1, mapOfMalta.size());
    }


/*@Test
    public void testCalculator() throws InterruptedException {
        //Exercise
        //find searchfield
        WebElement searchField = webdriver.findElement(By.name("q"));
        //enter malta in serachfield
        searchField.sendKeys("5+2");
        Thread.sleep(1000);

        //find searchbutton
        WebElement searchButton = webdriver.findElement(By.name("btnK"));
        //submit searchbutton
        searchButton.submit();
        Thread.sleep(1000);

        //Verify
        List<WebElement> calculatorElements = webdriver.findElements(By.xpath("//h2[text() = 'Calculator result']"));
        Assertions.assertEquals(1,calculatorElements.size());

        calculatorElements = webdriver.findElements(By.xpath("//span[text() = '  5 + 2 =  ']"));
        Assertions.assertEquals(1,calculatorElements.size());

    List<WebElement> ResultElement = webdriver.findElements(By.xpath("//span[text() = '7']"));
    Assertions.assertEquals(1,ResultElement.size());
    }
}

 */
}