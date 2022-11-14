package test.cps3230.screenscraper.stubs;

import edu.um.cps3230.Website.SearchService;

public class SearchIsNotWorking implements SearchService {
    public boolean isSearchWorking() {
        return false;
    }
}
