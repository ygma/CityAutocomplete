package com.example.CityAutocomplete;

import com.example.CityAutocomplete.controller.SuggestionCity;
import com.example.CityAutocomplete.controller.SuggestionResponse;
import com.example.CityAutocomplete.service.City;
import com.example.CityAutocomplete.service.load.CityLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CityAutocompleteControllerTests {

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CityLoader cityLoader;

    @BeforeEach
    private void beforeEach() {
        objectMapper = new ObjectMapper();

        when(cityLoader.load()).thenReturn(asList(
                new City("Aaa", "Aaa", 0, 0),
                new City("Aba", "Aba", 0, 0),
                new City("Abb", "Abb", 0, 0),
                new City("Aca", "Aca", 0, 0)
        ));
    }

    @Test
    @SneakyThrows
    public void should_return_200_and_empty_response_if_not_found() {
        String contentAsString = mockMvc.perform(get("/suggestions?q=SomeRandomCityInTheMiddleOfNowhere"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        SuggestionResponse actual = objectMapper.readValue(contentAsString, SuggestionResponse.class);

        assertEquals(new SuggestionResponse(emptyList()), actual);
    }

    @Test
	@SneakyThrows
    public void should_return_city_name_and_geo_location_when_city_name_is_match() {
		String contentAsString = mockMvc.perform(get("/suggestions?q=Ab"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		SuggestionResponse actual = objectMapper.readValue(contentAsString, SuggestionResponse.class);

		SuggestionResponse expected = new SuggestionResponse(asList(
				new SuggestionCity("Aba", 0, 0, 1),
				new SuggestionCity("Abb", 0, 0, 1)));

		assertThat(expected.getSuggestions(), containsInAnyOrder(actual.getSuggestions().toArray()));
    }
}
