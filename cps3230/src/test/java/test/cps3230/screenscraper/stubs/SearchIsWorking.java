package test.cps3230.screenscraper.stubs;

import edu.um.cps3230.Website.SearchService;

public class SearchIsWorking implements SearchService {
    public boolean isSearchWorking() {
        return true;
    }
}
