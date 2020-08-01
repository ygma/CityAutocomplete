package com.example.city.autocomplete.service.search;

import com.example.city.autocomplete.service.City;
import com.example.city.autocomplete.service.load.CityLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@Lazy
public class CitySearchImpl implements CitySearch {
    private SearchTree searchTree;

    @Autowired
    private CityLoader cityLoader;

    @PostConstruct
    public void postConstruct() {
        initialize();
    }

    // For unit test
    @Override
    public void initialize() {
        List<City> cities = cityLoader.load();
        searchTree = new SearchTree(cities);
    }

    @Override
    public List<City> search(String query, Float latitude, Float longitude) {
        return searchTree.search(query, latitude, longitude);
    }
}
