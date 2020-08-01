package com.example.CityAutocomplete.service.search;

import com.example.CityAutocomplete.service.City;
import com.example.CityAutocomplete.service.load.CityLoader;
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
        List<City> cities = cityLoader.load();
        searchTree = new SearchTree(cities);
    }

    @Override
    public List<City> search(String query) {
        return searchTree.search(query);
    }
}
