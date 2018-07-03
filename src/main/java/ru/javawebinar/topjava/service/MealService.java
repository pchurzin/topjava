package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    List<Meal> getAll();

    Meal getById(long id);

    void add(Meal meal);

    void remove(long id);

    void update(long id, Meal newMeal);
}
