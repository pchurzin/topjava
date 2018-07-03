package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealServiceMemory implements MealService {
    private final Map<Long, Meal> meals = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1L);

    public MealServiceMemory() {
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addMeal(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> getMeals() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public void addMeal(Meal meal) {
        if (meal == null) {
            return;
        }
        Long id = counter.getAndIncrement();
        meal.setId(id);
        meals.put(id, meal);
    }

    @Override
    public void removeMeal(long id) {
        meals.remove(id);
    }

    @Override
    public void updateMeal(long id, Meal newMeal) {
        if (newMeal == null) {
            return;
        }
        newMeal.setId(id);
        meals.put(id, newMeal);
    }

    @Override
    public Meal getMealById(long id) {
        return meals.get(id);
    }
}