package com.example.city.autocomplete.service.load;

import com.example.city.autocomplete.service.City;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Lazy
public class CityLoaderImpl implements CityLoader {
    @Override
    @SneakyThrows
    public List<City> load() {
        InputStream inputStream = CityLoaderImpl.class.getClassLoader().getResourceAsStream("cities15000.txt");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            return br.lines()
                    .map(line -> parseCity(line))
                    .collect(Collectors.toList());
        }
    }

    private City parseCity(String line) {
        String[] strings = line.split("\t");
        if (strings.length < 9) {
            throw new IllegalArgumentException("Unknown city source");
        }
        return new City(
                strings[1],
                strings[2],
                Float.parseFloat(strings[4]),
                Float.parseFloat(strings[5]),
                strings[8]);
    }
}
