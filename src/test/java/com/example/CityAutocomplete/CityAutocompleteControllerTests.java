package com.example.CityAutocomplete;

import com.example.CityAutocomplete.controller.SuggestionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CityAutocompleteControllerTests {

	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	private void beforeEach() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void should_return_200_and_empty_response_if_not_found() throws Exception {
		String contentAsString = mockMvc.perform(get("/suggestions?q=SomeRandomCityInTheMiddleOfNowhere"))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		SuggestionResponse actual = objectMapper.readValue(contentAsString, SuggestionResponse.class);

		assertEquals(new SuggestionResponse(Collections.emptyList()), actual);
	}

}
