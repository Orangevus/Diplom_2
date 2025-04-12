package ru.praktikum.burgers.api.model;

import java.util.List;

public class Order {

    private List<String> ingredients;

    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }
   }
