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
import static org.assertj.core.api.Assertions.assertThat;

class ScoreTests extends ApiBaseTest {

    @BeforeEach
    private void beforeEach() {
        mockCities(asList(
                new City("Aba", "Aba", 1, 1, "CA"),
                new City("Abb", "Abb", 2, 2, "CA")));
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    @SneakyThrows
    public void should_return_suggestion_cities_sorted_by_score_when_city_name_is_matched(TestCase testCase) {
        String url = String.join("",
                "/suggestions?q=", testCase.q,
                "&latitude=", testCase.latitude.toString(),
                "&longitude=", testCase.longitude.toString());

        SuggestionResponse actual = callApi(url, SuggestionResponse.class);

        assertThat(actual.getSuggestions())
                .usingElementComparatorIgnoringFields("score")
                .isEqualTo(testCase.expected);
    }

    static Stream<TestCase> dataProvider() {
        return Stream.of(
                new TestCase("Ab", 1.1f, 1.1f, asList(
                        new SuggestionCity("Aba, CA", 1, 1, 1),
                        new SuggestionCity("Abb, CA", 2, 2, 1)))
        );
    }

    @Data
    @AllArgsConstructor
    private static class TestCase {
        private String q;
        private Float latitude;
        private Float longitude;
        private List<SuggestionCity> expected;
    }
}
