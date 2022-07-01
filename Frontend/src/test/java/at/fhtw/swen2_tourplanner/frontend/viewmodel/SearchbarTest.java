package at.fhtw.swen2_tourplanner.frontend.viewmodel;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchbarTest {

    private Searchbar searchbar = new Searchbar();


    @Test
    void testSearch() {
        String searchText = "TestSearch";
        searchbar.getSearchText().setValue(searchText);
        searchbar.registerObserver((String observerText) -> assertThat(observerText).isEqualTo(searchText));
        searchbar.search();
    }

    @Test
    void testSearchClear() {
        String searchText = "TestSearch";
        searchbar.getSearchText().setValue(searchText);
        searchbar.clearSearch();
        assertThat(searchbar.getSearchText().getValue()).isEqualTo("");
    }

}