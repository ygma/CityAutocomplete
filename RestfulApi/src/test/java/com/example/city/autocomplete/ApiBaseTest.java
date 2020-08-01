package com.example.city.autocomplete;

import com.example.city.autocomplete.service.City;
import com.example.city.autocomplete.service.load.CityLoader;
import com.example.city.autocomplete.service.search.CitySearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiBaseTest {

    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected CityLoader cityLoader;
    @Autowired
    private CitySearch citySearch;

    @BeforeEach
    protected void beforeEachBase() {
        objectMapper = new ObjectMapper();
    }

    protected <TResponse> TResponse callApi(String url, Class<TResponse> responseClass) throws Exception {
        String contentAsString = mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(contentAsString, responseClass);
    }

    protected void mockCities(List<City> cities) {
        when(cityLoader.load()).thenReturn(cities);
        citySearch.initialize();
    }
}
