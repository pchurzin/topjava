package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS1.forEach(meal -> save(meal, 1));
        MealsUtil.MEALS2.forEach(meal -> save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.containsKey(userId) && (null != repository.get(userId).remove(id));
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.containsKey(userId) ? repository.get(userId).get(id) : null;
    }

    @Override
    public List<Meal> get(int userId, Predicate<Meal> filter, Comparator<Meal> sort) {
        if (!repository.containsKey(userId)) {
            return Collections.emptyList();
        }

        if (sort == null) {
            sort = Comparator.comparing(Meal::getDateTime).reversed();
        }

        if (filter == null) {
            filter = meal -> true;
        }

        return repository.get(userId).values()
                .stream()
                .filter(filter)
                .sorted(sort)
                .collect(Collectors.toList());
    }
}