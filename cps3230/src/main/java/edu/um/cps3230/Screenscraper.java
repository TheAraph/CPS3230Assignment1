package edu.um.cps3230;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import edu.um.cps3230.Product;

public class Screenscraper {

    //Initialize Products LinkedList
    public static List<Product> products = new LinkedList<>();
    public static WebDriver driver;

    //Initialize holder for Products in JSON format
    static String ProductsJSONList;
    public static int responseDELETE;
    public static int responsePOST;

    public static String currentURL;

    public Screenscraper() {

    }

    //Open Website
    public static void openWebsiteandSearch() throws InterruptedException {
        driver = new SafariDriver();
        //get scan malta website
        driver.get("https://www.scanmalta.com/");

        //Find search field element by id
        WebElement searchField = driver.findElement(By.id("search"));
        //Type Laptop
        searchField.sendKeys("Laptop");
        //Submit searchfield
        searchField.submit();
        Thread.sleep(3000);
        currentURL = driver.getCurrentUrl();
    }

    //Method for adding products to the products linked list
    public static boolean addProduct(Product product) {
        products.add(product);
        return true;
    }

    //Web Scraper
    public static void ScrapeProduct() throws InterruptedException {
        //Wait 10000 milliseconds (for content to load)
        Thread.sleep(10000);
        //For loop to get 5 entries
        for (int i = 1; i < 6; i++) {
            //Take product header by xpath
            String headerXPath = "//*[@id=\"maincontent\"]/div[3]/div[1]/div[5]/div[2]/ol/li[" + i + "]/div/div[2]/strong/a/text()";
            String headtemp = driver.findElement(By.xpath(headerXPath)).getText();

            //Take description by xpath
            String descXPath = "/html/body/div[1]/main/div[3]/div[1]/div[5]/div[2]/ol/li[" + i + "]/div/div[2]/div[1]/span[2]";
            String desctemp = driver.findElement(By.xpath(descXPath)).getText();

            //Url
            String urlXPath = "/html/body/div[1]/main/div[3]/div[1]/div[5]/div[2]/ol/li[" + i + "]/div/div[1]/a";
            String urltemp = driver.findElement(By.xpath(urlXPath)).getAttribute("href");

            //IMG url
            String imgurlXPath = "/html/body/div[1]/main/div[3]/div[1]/div[5]/div[2]/ol/li[" + i + "]/div/div[1]/a/span/span/img[1]";
            String imgtemp = driver.findElement(By.xpath(imgurlXPath)).getAttribute("src");

            //Price
            String priceXPath = "/html/body/div[1]/main/div[3]/div[1]/div[5]/div[2]/ol/li[" + i + "]/div/div[2]/div[2]/span[1]/span/span[2]/span";
            String pricetemp = driver.findElement(By.xpath(priceXPath)).getText();

            //Remove all euro signs and commas
            pricetemp = pricetemp.replaceAll("€", "");
            pricetemp = pricetemp.replaceAll(",", "");

            //Take price as double and x100 to make price in cents
            double DoublePriceInCents = Double.valueOf(pricetemp) * 100;
            //Back to integer value
            int PriceInCents = (int) DoublePriceInCents;

            //Add product (with predetermined alert type & user id)
            addProduct(new Product(6, headtemp, desctemp, urltemp, imgtemp, "3e0dc9fb-291e-438e-9fbb-00cc6865af94", PriceInCents));
        }

    }

    public static void DeleteRequest() throws IOException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            //Execute DELETE
            HttpDelete httpget = new HttpDelete("https://api.marketalertum.com/Alert?userId=3e0dc9fb-291e-438e-9fbb-00cc6865af94");
            HttpResponse response = httpclient.execute(httpget);

            //get status code (for test)
            responseDELETE = response.getStatusLine().getStatusCode();
            String responseBody = new BasicResponseHandler().handleResponse(response);

            //output response
            System.out.println("Response: " + responseBody);
        }
    }

        //Post Request
        public static void POSTRequest () throws IOException {

            //Iterate through all products one by one (since we cannot send a JSON string with multiple entries)
            for (int a = 0; a < products.size(); a++) {
                //Import Object Mapper from Jackson library
                ObjectMapper mapper = new ObjectMapper();
                //Convert list to JSON syntax
                ProductsJSONList = mapper.writeValueAsString(products.get(a));
                //Remove all square brackets from JSON
                ProductsJSONList = ProductsJSONList.replaceAll("]", "");
                ProductsJSONList = ProductsJSONList.replaceAll("\\[", "");

                CloseableHttpClient client = HttpClients.createDefault();
                //Create new Post to alerts website
                HttpPost httpPost = new HttpPost("https://api.marketalertum.com/Alert");

                //set entity as current json product
                StringEntity entity = new StringEntity(ProductsJSONList);
                httpPost.setEntity(entity);

                //accept json file
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                //execute post
                CloseableHttpResponse response = client.execute(httpPost);

                //hold response code
                responsePOST = response.getStatusLine().getStatusCode();
                client.close();

                //show response code
                System.out.println("Response Code = " + responsePOST);
            }


        }
    // main class
        public static void main (String[]args) throws InterruptedException, IOException {
            openWebsiteandSearch();
            ScrapeProduct();
            POSTRequest();
            DeleteRequest(); // <--- make this a comment to ensure that POSTRequests are working obviously
        }
    }

