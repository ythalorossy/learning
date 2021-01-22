package com.pluralsight.bdd.loyalty_cards;

public class MorningFressnessMember {
    private final String name;
    private final SuperSmoothieSchema schema;
    private int points;

    public MorningFressnessMember(String name, SuperSmoothieSchema schema) {
        this.name = name;
        this.schema = schema;
    }

    public void orders(Integer amount, String drink) {
        points += schema.getPointsForDrink(drink) * amount;
    }

    public int getPoints() {
        return points;

    }
}
