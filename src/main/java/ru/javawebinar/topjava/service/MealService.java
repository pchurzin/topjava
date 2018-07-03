package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    List<Meal> getMeals();

    Meal getMealById(long id);

    void addMeal(Meal meal);

    void removeMeal(long id);

    void updateMeal(long id, Meal newMeal);
}
