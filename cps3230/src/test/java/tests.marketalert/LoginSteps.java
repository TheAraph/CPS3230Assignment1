package tests.marketalert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static edu.um.cps3230.Screenscraper.driver;

public class LoginSteps {
    WebDriver driver;
    public static String iconDisplayed;

    @Given("I am a user of marketalertum")
    public void iAmAUserOfMarketalertum() {
        //create new safaridriver instance
        driver = new SafariDriver();
        //get marketalert website
        driver.get("https://www.marketalertum.com/");
    }

    @When("I login using valid credentials")
    public void iLoginUsingValidCredentials() throws InterruptedException {
        //Set personal userId
        String userId = ("3e0dc9fb-291e-438e-9fbb-00cc6865af94");

        //find login page button
        String XPathToLoginPage = "/html/body/header/nav/div/div/ul/li[3]/a";
        WebElement LoginPage = driver.findElement(By.xpath(XPathToLoginPage));
        //click
        LoginPage.click();

        //wait
        Thread.sleep(5000);

        //find userid field
        WebElement userIDField = driver.findElement(By.id("UserId"));
        //type in userId
        userIDField.sendKeys(userId);
        //submit
        userIDField.submit();
    }

    @Then("I should see my alerts")
    public void iShouldSeeMyAlerts() throws InterruptedException {
        // wait
        Thread.sleep(5000);
        //assert that we are on alerts page
        String alertImage = driver.findElement(By.tagName("h1")).getText();
        Assertions.assertEquals("Latest alerts for Adam Ryan Ali Farag", alertImage);
        driver.quit();
    }

    @When("I login using invalid credentials")
    public void iLoginUsingInvalidCredentials() throws InterruptedException {
        //set invalid user id
        String userId = ("12345");

        Thread.sleep(1000);
        String XPathToLoginPage = "/html/body/header/nav/div/div/ul/li[3]/a";
        WebElement LoginPage = driver.findElement(By.xpath(XPathToLoginPage));
        LoginPage.click();

        Thread.sleep(1000);
        WebElement userIDField = driver.findElement(By.id("UserId"));
        userIDField.sendKeys(userId);
        userIDField.submit();
    }

    @Then("I should see the login screen again")
    public void iShouldSeeTheLoginScreenAgain() throws InterruptedException {
        Thread.sleep(3000);
        //make sure we are on the login screen
        String url = driver.getCurrentUrl();
        Assertions.assertEquals("https://www.marketalertum.com/Alerts/Login", url);
        driver.quit();
    }

    @Given("I am an administrator of the website and I upload {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAlerts(int arg0) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //set delete request to rest api & execute
        HttpDelete httpget = new HttpDelete("https://api.marketalertum.com/Alert?userId=3e0dc9fb-291e-438e-9fbb-00cc6865af94");
        HttpResponse response = httpclient.execute(httpget);

        //set & output response into console
        String responseBody = new BasicResponseHandler().handleResponse(response);
        System.out.println("Response: " + responseBody);

        //iterate and create <arg0> alerts
        ArrayList<String> Alerts = new ArrayList<>();
        for (int i = 0; i < arg0; i++) {
            Alerts.add("{\"alertType\": 1, \"heading\": \"Jumper Windows 11 LaptopTEST\", \"description\": \"Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD\", \"url\": \"https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth\", \"imageUrl\" : \"https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg\", \"postedBy\": \"3e0dc9fb-291e-438e-9fbb-00cc6865af94\", \"priceInCents\": 24999}");
        }

        //POST
        for (int a = 0; a < Alerts.size(); a++) {
            CloseableHttpClient client = HttpClients.createDefault();
            //Create new Post to alerts website
            HttpPost httpPost = new HttpPost("https://api.marketalertum.com/Alert");

            //set entity as current json product
            StringEntity entity = new StringEntity(Alerts.get(a));
            httpPost.setEntity(entity);

            //accept json file
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //execute post
            CloseableHttpResponse POSTresponse = client.execute(httpPost);

            //hold response code
            int responsePOST = POSTresponse.getStatusLine().getStatusCode();
            client.close();
            System.out.print("Response Code : " + responsePOST);


        }
    }


    @When("I view a list of alerts")
    public void iViewAListOfAlerts() throws InterruptedException {
        String userId = ("3e0dc9fb-291e-438e-9fbb-00cc6865af94");

        String XPathToLoginPage = "/html/body/header/nav/div/div/ul/li[3]/a";
        WebElement LoginPage = driver.findElement(By.xpath(XPathToLoginPage));
        LoginPage.click();

        Thread.sleep(5000);
        WebElement userIDField = driver.findElement(By.id("UserId"));
        userIDField.sendKeys(userId);
        userIDField.submit();


    }

    @Then("each alert should contain an icon")
    public void eachAlertShouldContainAnIcon() throws InterruptedException {
        Thread.sleep(3000);
        driver.get("https://www.marketalertum.com/Alerts/List");
        //create list to hold icons
        List<WebElement> icons = new LinkedList<>();
        icons = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr/td/h4/img"));
        //verify - compare icon list size to expected size
        Assertions.assertEquals(3, icons.size());
    }

    @And("each alert should contain a heading")
    public void eachAlertShouldContainAHeading() throws InterruptedException {

        //wait 5 seconds
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        List<WebElement> headings = new LinkedList<>();
        headings = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr/td/h4"));

        //verify
        Assertions.assertEquals(3, headings.size());
    }

    @And("each alert should contain a description")
    public void eachAlertShouldContainADescription() throws InterruptedException {
        driver.get("https://www.marketalertum.com/Alerts/List");
        Thread.sleep(3000);

        List<WebElement> desc = new LinkedList<>();
        desc = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr[3]/td"));
        Assertions.assertEquals(3, desc.size());
    }

    @And("each alert should contain an image")
    public void eachAlertShouldContainAnImage() throws InterruptedException {
        driver.get("https://www.marketalertum.com/Alerts/List");
        Thread.sleep(3000);

        List<WebElement> img = new LinkedList<>();
        img = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr/td/img"));
        Assertions.assertEquals(3, img.size());
    }

    @And("each alert should contain a price")
    public void eachAlertShouldContainAPrice() throws InterruptedException {
        driver.get("https://www.marketalertum.com/Alerts/List");
        Thread.sleep(3000);

        List<WebElement> price = new LinkedList<>();
        price = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr/td/b"));
        Assertions.assertEquals(3, price.size());
    }

    @And("each alert should contain a link to the original product website")
    public void eachAlertShouldContainALinkToTheOriginalProductWebsite() throws InterruptedException {
        driver.get("https://www.marketalertum.com/Alerts/List");
        Thread.sleep(3000);

        List<WebElement> link = new LinkedList<>();
        link = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr/td/a"));
        Assertions.assertEquals(3, link.size());
        driver.quit();
    }

    @Given("I am an administrator of the website and I upload more than {int} alerts")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadMoreThanAlerts(int arg0) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //set delete request to rest api & execute
        HttpDelete httpget = new HttpDelete("https://api.marketalertum.com/Alert?userId=3e0dc9fb-291e-438e-9fbb-00cc6865af94");
        HttpResponse response = httpclient.execute(httpget);

        //set & output response into console
        String responseBody = new BasicResponseHandler().handleResponse(response);
        System.out.println("Response: " + responseBody);

        //add arg0+1 alerts (in this case 5+1)
        ArrayList<String> Alerts = new ArrayList<>();
        for (int i = 0; i < (arg0 + 1); i++) {
            Alerts.add("{\"alertType\": 1, \"heading\": \"TEST\", \"description\": \"Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD\", \"url\": \"https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth\", \"imageUrl\" : \"https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg\", \"postedBy\": \"3e0dc9fb-291e-438e-9fbb-00cc6865af94\", \"priceInCents\": 24999}");
        }
        for (int a = 0; a < Alerts.size(); a++) {
            CloseableHttpClient client = HttpClients.createDefault();
            //Create new Post to alerts website
            HttpPost httpPost = new HttpPost("https://api.marketalertum.com/Alert");

            //set entity as current json product
            StringEntity entity = new StringEntity(Alerts.get(a));
            httpPost.setEntity(entity);

            //accept json file
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            //execute post
            CloseableHttpResponse POSTresponse = client.execute(httpPost);

            //hold response code
            int responsePOST = POSTresponse.getStatusLine().getStatusCode();
            client.close();
            System.out.println("Response Code : " + responsePOST);


        }
    }

    @Then("I should see {int} alerts")
    public void iShouldSeeAlerts(int arg0) throws InterruptedException {
        Thread.sleep(5000);
        List<WebElement> AlertLimit = driver.findElements(By.xpath("/html/body/div/main/table/tbody/tr/td/h4"));
        Assertions.assertEquals(arg0, AlertLimit.size());
        Thread.sleep(1000);
        driver.quit();
    }

    @Given("I am an administrator of the website and I upload an alert of type {int}")
    public void iAmAnAdministratorOfTheWebsiteAndIUploadAnAlertOfType(int arg0) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        //set delete request to rest api & execute
        HttpDelete httpget = new HttpDelete("https://api.marketalertum.com/Alert?userId=3e0dc9fb-291e-438e-9fbb-00cc6865af94");
        HttpResponse response = httpclient.execute(httpget);

        //set & output response into console
        String responseBody = new BasicResponseHandler().handleResponse(response);
        System.out.println("Response: " + responseBody);
        ArrayList<String> Alerts = new ArrayList<>();

        //set input number to an alert type for the icon
        if (arg0 == 1) {
            Alerts.add("{\"alertType\": 1, \"heading\": \"TEST\", \"description\": \"Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD\", \"url\": \"https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth\", \"imageUrl\" : \"https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg\", \"postedBy\": \"3e0dc9fb-291e-438e-9fbb-00cc6865af94\", \"priceInCents\": 24999}");
        } else if (arg0 == 2) {
            Alerts.add("{\"alertType\": 2, \"heading\": \"TEST\", \"description\": \"Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD\", \"url\": \"https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth\", \"imageUrl\" : \"https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg\", \"postedBy\": \"3e0dc9fb-291e-438e-9fbb-00cc6865af94\", \"priceInCents\": 24999}");
        } else if (arg0 == 3) {
            Alerts.add("{\"alertType\": 3, \"heading\": \"TEST\", \"description\": \"Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD\", \"url\": \"https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth\", \"imageUrl\" : \"https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg\", \"postedBy\": \"3e0dc9fb-291e-438e-9fbb-00cc6865af94\", \"priceInCents\": 24999}");
        } else if (arg0 == 4) {
            Alerts.add("{\"alertType\": 4, \"heading\": \"TEST\", \"description\": \"Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD\", \"url\": \"https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth\", \"imageUrl\" : \"https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg\", \"postedBy\": \"3e0dc9fb-291e-438e-9fbb-00cc6865af94\", \"priceInCents\": 24999}");
        } else if (arg0 == 5) {
            Alerts.add("{\"alertType\": 5, \"heading\": \"TEST\", \"description\": \"Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD\", \"url\": \"https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth\", \"imageUrl\" : \"https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg\", \"postedBy\": \"3e0dc9fb-291e-438e-9fbb-00cc6865af94\", \"priceInCents\": 24999}");
        } else if (arg0 == 6) {
            Alerts.add("{\"alertType\": 6, \"heading\": \"TEST\", \"description\": \"Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD\", \"url\": \"https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth\", \"imageUrl\" : \"https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg\", \"postedBy\": \"3e0dc9fb-291e-438e-9fbb-00cc6865af94\", \"priceInCents\": 24999}");
        }

        CloseableHttpClient client = HttpClients.createDefault();
        //Create new Post to alerts website
        HttpPost httpPost = new HttpPost("https://api.marketalertum.com/Alert");

        //set entity as current json product
        StringEntity entity = new StringEntity(Alerts.get(0));
        httpPost.setEntity(entity);

        //accept json file
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        //execute post
        CloseableHttpResponse POSTresponse = client.execute(httpPost);

        //hold response code
        int responsePOST = POSTresponse.getStatusLine().getStatusCode();
        client.close();
        System.out.println("Response Code : " + responsePOST);
    }

    @And("the icon displayed should be {int}")
    public void theIconDisplayedShouldBe(int arg0) throws InterruptedException {
        //set inputted number as expected response
        if (arg0 == 1) {
            iconDisplayed = "icon-car.png";
        } else if (arg0 == 2) {
            iconDisplayed = "icon-boat.png";
        } else if (arg0 == 3) {
            iconDisplayed = "icon-property-rent.png";
        } else if (arg0 == 4) {
            iconDisplayed = "icon-property-buy.png";
        } else if (arg0 == 5) {
            iconDisplayed = "icon-toys.png";
        } else if (arg0 == 6) {
            iconDisplayed = "icon-electronics.png";
        }

        //create new instance
        driver = new SafariDriver();
        driver.get("https://www.marketalertum.com/");

        String userId = ("3e0dc9fb-291e-438e-9fbb-00cc6865af94");

        //find login page
        String XPathToLoginPage = "/html/body/header/nav/div/div/ul/li[3]/a";
        WebElement LoginPage = driver.findElement(By.xpath(XPathToLoginPage));
        LoginPage.click();

        //enter username and submit
        Thread.sleep(3000);
        WebElement userIDField = driver.findElement(By.id("UserId"));
        userIDField.sendKeys(userId);
        userIDField.submit();
        Thread.sleep(3000);

        //find iconname (without full link)
        String IconName = driver.findElement(By.xpath("/html/body/div/main/table[1]/tbody/tr[1]/td/h4/img")).getAttribute("src");
        IconName = IconName.replaceAll(".jpg",".png");
        IconName = IconName.replaceAll("https://www.marketalertum.com/images/","");
        Assertions.assertEquals(iconDisplayed, IconName);
        driver.quit();
    }
}