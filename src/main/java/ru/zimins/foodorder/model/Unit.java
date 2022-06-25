package ru.zimins.foodorder.model;

public enum Unit {
    KG("Килограмм"),
    G("Грамм"),
    L("Литр"),
    PC("Штука");

    final String description;

    Unit(String description) {
        this.description = description;
    }
}
