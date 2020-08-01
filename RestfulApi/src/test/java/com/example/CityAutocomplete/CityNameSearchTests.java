package com.example.CityAutocomplete;

import com.example.CityAutocomplete.controller.SuggestionCity;
import com.example.CityAutocomplete.controller.SuggestionResponse;
import com.example.CityAutocomplete.service.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

class CityNameSearchTests extends ApiBaseTest {

    @BeforeEach
    private void beforeEach() {
        mockCities(asList(
                new City("Aaa", "Aaa", 0, 0),
                new City("Aaa Aa", "Aaa Aa", 0, 0),
                new City("Aba", "Aba", 0, 0),
                new City("Abb", "Abb", 0, 0),
                new City("Aca", "Aca", 0, 0)
        ));
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    @SneakyThrows
    public void should_return_city_name_and_geo_location_when_city_name_is_matched(TestCase testCase) {
        SuggestionResponse actual = callApi(
                "/suggestions?q=" + testCase.q,
                SuggestionResponse.class);

        assertThat(testCase.expected, containsInAnyOrder(actual.getSuggestions().toArray()));
    }

    static Stream<TestCase> dataProvider() {
        return Stream.of(
                new TestCase("SomeRandomCityInTheMiddleOfNowhere", emptyList()),
                new TestCase("Ab", asList(
                        new SuggestionCity("Aba", 0, 0, 1),
                        new SuggestionCity("Abb", 0, 0, 1))),
                new TestCase("ab", asList(
                        new SuggestionCity("Aba", 0, 0, 1),
                        new SuggestionCity("Abb", 0, 0, 1))),
                new TestCase("Abb", asList(
                        new SuggestionCity("Abb", 0, 0, 1))),
                new TestCase("Abbb", emptyList()),
                new TestCase("Aaa A", asList(
                        new SuggestionCity("Aaa Aa", 0, 0, 1)))
        );
    }

    @Data
    @AllArgsConstructor
    private static class TestCase {
        private String q;
        private List<SuggestionCity> expected;
    }
}
