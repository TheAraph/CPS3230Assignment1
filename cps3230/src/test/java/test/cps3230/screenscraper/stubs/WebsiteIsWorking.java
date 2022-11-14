package test.cps3230.screenscraper.stubs;

import edu.um.cps3230.Website.WebsiteService;

public class WebsiteIsWorking implements WebsiteService {
    public boolean isWebsiteWorking() {
        return true;
    }
}
