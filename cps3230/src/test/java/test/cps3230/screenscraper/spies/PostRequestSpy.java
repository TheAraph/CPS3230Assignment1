package test.cps3230.screenscraper.spies;

import edu.um.cps3230.Product;
import edu.um.cps3230.utils.PostRequest;

public class PostRequestSpy implements PostRequest {
    //not needed due to use of response code
    public int numPostRequests = 0;

    public void postProduct(Product product) {
        numPostRequests++;
    }
}
