package test.cps3230.screenscraper;

import edu.um.cps3230.Product;
import edu.um.cps3230.Screenscraper;
import org.junit.jupiter.api.*;
import test.cps3230.screenscraper.stubs.WebsiteIsNotWorking;
import test.cps3230.screenscraper.stubs.WebsiteIsWorking;

import java.io.IOException;

import static edu.um.cps3230.Screenscraper.currentURL;
import static edu.um.cps3230.Screenscraper.driver;

public class screenscraper{

Screenscraper scraper;

    @Test
    public void TestAdditionOfProductsToProductsLinkedList(){

        //exercise
        scraper.addProduct(new Product(1,"test","test","test","test","test", 1));

        //verify
        Assertions.assertEquals(1, scraper.products.size());
    }

    @Test
    public void TestPostRequest() throws IOException {

        //setup
        scraper = new Screenscraper();

        //exercise
        scraper.addProduct(new Product(6, "Jumper Windows 11 Laptop", "Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD", "https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth", "https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg", "3e0dc9fb-291e-438e-9fbb-00cc6865af94", 24999));
        scraper.POSTRequest();
        Assertions.assertEquals(201, scraper.responsePOST);

        //teardown
        scraper.DeleteRequest();
    }

    @Test
    public void TestPostRequestIncorrectUserIDInput() throws IOException{
        //setup
        scraper = new Screenscraper();

        //exercise
        scraper.addProduct(new Product(6, "Jumper Windows 11 Laptop", "Jumper Windows 11 Laptop 1080P Display,12GB RAM 256GB SSD", "https://www.amazon.co.uk/Windows-Display-Ultrabook-Processor-Bluetooth", "https://m.media-amazon.com/images/I/712Xf2LtbJL._AC_SX679_.jpg", "21", 24999));
        scraper.POSTRequest();
        Assertions.assertEquals(400, scraper.responsePOST);
    }

    @Test
    public void TestDeleteRequest() throws IOException {

        //setup
        scraper = new Screenscraper();

        //exercise
        scraper.DeleteRequest();

        //verify
        Assertions.assertEquals(200, scraper.responseDELETE);
    }


    @Test
    public void SiteIsLoadingStub(){
        //Exercise
        WebsiteIsWorking webWorks = new WebsiteIsWorking();
        boolean result = webWorks.isWebsiteWorking();

        //Verify
        Assertions.assertTrue(result);
    }

    @Test
    public void SiteIsNotLoadingStub(){

        //Exercise
        WebsiteIsNotWorking webDoesntWork = new WebsiteIsNotWorking();
        boolean result = webDoesntWork.isWebsiteWorking();

        //Verify
        Assertions.assertFalse(result);

    }

    @Test
    public void TestSearchIsWorking() throws InterruptedException {
        //Exercise
        scraper.openWebsiteandSearch();

        //Verify
        Assertions.assertEquals("https://www.scanmalta.com/shop/catalogsearch/result/?q=Laptop", currentURL);

        //Teardown
        driver.quit();
    }

}
