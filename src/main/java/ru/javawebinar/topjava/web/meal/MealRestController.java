package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected static final Logger log = getLogger(MealRestController.class);

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    private List<Meal> get(Predicate<Meal> filter, Comparator<Meal> sort) {
        log.info("get with filter {} and sort {}", filter, sort);
        return service.get(authUserId(), filter, sort);
    }

    public List<MealWithExceed> get() {
        log.info("get All");
        return get(null, null, null, null);
    }

    public List<MealWithExceed> get(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("get with startDate = {}, endDate = {}, startTime = {}, endTime = {}",
                startDate, endDate, startTime, endTime);
        LocalDate finalStartDate = startDate != null ? startDate : LocalDate.MIN;
        LocalDate finalEndDate = endDate != null ? endDate : LocalDate.MAX;
        Predicate<Meal> filter =
                meal ->
                        DateTimeUtil.isBetween(meal.getDate(), finalStartDate, finalEndDate);
        List<Meal> meals = get(filter, null);
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : LocalTime.MAX;
        return MealsUtil.getFilteredWithExceeded(meals, authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with {}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }
}