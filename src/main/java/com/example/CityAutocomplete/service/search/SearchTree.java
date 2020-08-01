package com.example.CityAutocomplete.service.search;

import com.example.CityAutocomplete.service.City;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

class SearchTree {
    private TreeNode tree;

    public SearchTree(List<City> cities) {
        tree = new TreeNode("");

        initializeTree(cities);
    }

    public List<City> search(String query, Float latitude, Float longitude) {
        TreeNode currentNode = this.tree;
        query = query.toLowerCase();

        for (int i = 0; i < query.length(); i++) {
            String prefix = query.substring(0, i + 1);
            Map<String, TreeNode> childNodeMap = currentNode.getChildNodeMap();

            if (!childNodeMap.containsKey(prefix)) {
                currentNode = null;
                break;
            }
            currentNode = childNodeMap.get(prefix);
        }

        return currentNode == null
                ? Collections.emptyList()
                : currentNode.getTop10Cities(latitude, longitude);
    }

    private void initializeTree(List<City> cities) {
        for (City city : cities) {
            TreeNode currentNode = tree;

            initializeTreeNode(city, currentNode);
        }
    }

    private void initializeTreeNode(City city, TreeNode currentNode) {
        String asciiName = city.getAsciiName().toLowerCase();
        int nameLength = asciiName.length();

        for (int i = 0; i <= nameLength; i++) {
            if (i == nameLength) {
                currentNode.setCity(city);
                break;
            }
            String prefix = asciiName.substring(0, i + 1);
            Map<String, TreeNode> childNodeMap = currentNode.getChildNodeMap();

            if (!childNodeMap.containsKey(prefix)) {
                childNodeMap.put(prefix, new TreeNode(prefix));
            }

            currentNode = childNodeMap.get(prefix);
        }
    }

    @Data
    private static class TreeNode {
        private String prefix;
        private Map<String, TreeNode> childNodeMap = new HashMap<>();
        private City city;

        public TreeNode(String prefix) {
            this.prefix = prefix;
        }

        public List<City> getTop10Cities(Float latitude, Float longitude) {
            List<City> cities = new ArrayList<>();
            if (city != null) {
                cities.add(city);
            }

            List<City> collect = childNodeMap.values().stream()
                    .flatMap(node -> node.getTop10Cities(latitude, longitude).stream())
                    .collect(Collectors.toList());

            cities.addAll(collect);
            return cities.stream()
                    .sorted(Comparator.comparing(city -> ((City)city).getScore(latitude, longitude)).reversed())
                    .limit(10).collect(Collectors.toList());
        }
    }
}
