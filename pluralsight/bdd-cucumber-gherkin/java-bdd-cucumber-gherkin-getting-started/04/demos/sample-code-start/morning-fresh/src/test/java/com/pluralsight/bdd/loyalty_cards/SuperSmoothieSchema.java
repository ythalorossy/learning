package com.pluralsight.bdd.loyalty_cards;

import java.util.HashMap;
import java.util.Map;

public class SuperSmoothieSchema {
    private final Map<String, Integer> pointsPerCategory = new HashMap<>();
    private DrinkCatalog catalog;

    public SuperSmoothieSchema(DrinkCatalog catalog) {
        this.catalog = catalog;
    }

    public void setPointsPerCategory(String category, Integer points) {
        pointsPerCategory.put(category, points);
    }

    public int getPointsForDrink(String drink) {
        return pointsPerCategory.get(categoryOfDrink(drink));
    }

    private String categoryOfDrink(String drink) {

        return catalog.getCategoryOfDrink(drink);
    }
}
