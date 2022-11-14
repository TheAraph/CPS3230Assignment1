package edu.um.cps3230;

public class Product {

    //initialise
    public int alertType;
    public String heading;
    public String description;
    public String url;
    public String imageUrl;
    public String postedBy;
    public int priceInCents;

    public Product(int alertType, String heading, String description, String url, String imageUrl, String postedBy, int priceInCents)
    {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.postedBy = postedBy;
        this.priceInCents = priceInCents;
    }

}
