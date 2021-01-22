package com.pluralsight.bdd.loyalty_cards;

import java.util.HashMap;
import java.util.Map;

public class DrinkCatalog {

    Map<String, String> drinkCategories = new HashMap<>();

    public void addDrink(String drink, String category) {
        this.drinkCategories.put(drink, category);
    }

    public String getCategoryOfDrink(String drink) {
        return drinkCategories.get(drink);
    }
}
