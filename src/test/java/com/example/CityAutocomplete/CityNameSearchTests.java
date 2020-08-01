package com.example.CityAutocomplete;

import com.example.CityAutocomplete.controller.SuggestionCity;
import com.example.CityAutocomplete.controller.SuggestionResponse;
import com.example.CityAutocomplete.service.City;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CityNameSearchTests extends ApiBaseTest {

    @BeforeEach
    private void beforeEach() {
        when(cityLoader.load()).thenReturn(asList(
                new City("Aaa", "Aaa", 0, 0),
                new City("Aba", "Aba", 0, 0),
                new City("Abb", "Abb", 0, 0),
                new City("Aca", "Aca", 0, 0)
        ));
    }

    @Test
    @SneakyThrows
    public void should_return_and_empty_response_if_not_found() {
        SuggestionResponse actual = callApi(
                "/suggestions?q=SomeRandomCityInTheMiddleOfNowhere",
                SuggestionResponse.class);

        assertEquals(new SuggestionResponse(emptyList()), actual);
    }

    @Test
	@SneakyThrows
    public void should_return_city_name_and_geo_location_when_city_name_is_match() {
        SuggestionResponse actual = callApi(
                "/suggestions?q=Ab",
                SuggestionResponse.class);

        SuggestionResponse expected = new SuggestionResponse(asList(
				new SuggestionCity("Aba", 0, 0, 1),
				new SuggestionCity("Abb", 0, 0, 1)));

		assertThat(expected.getSuggestions(), containsInAnyOrder(actual.getSuggestions().toArray()));
    }
}
