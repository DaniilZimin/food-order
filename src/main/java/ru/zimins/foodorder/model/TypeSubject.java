package ru.zimins.foodorder.model;

public enum TypeSubject {
    REPUBLIC("Республика"),
    EDGE("Край"),
    REGION("Регион"),
    FEDERAL_CITY("Город федерального значения"),
    AUTONOMOUS_REGION("Автономная область"),
    AUTONOMOUS_DISTRICT("Автономный округ");
    final String description;

    TypeSubject(String description) {
        this.description = description;
    }
}
