package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void get() {
        assertMatch(service.get(MEAL_0.getId(), ADMIN_ID), MEAL_0);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        service.delete(MEAL_8.getId(), USER_ID);
        service.get(MEAL_8.getId(), USER_ID);
    }

    @Test
    public void getBetweenDates() {
        LocalDate startDate = LocalDate.of(2018, Month.JULY, 14);
        LocalDate endDate = LocalDate.of(2018, Month.JULY, 14);
        List<Meal> actual = service.getBetweenDates(startDate, endDate, USER_ID);
        assertMatch(actual, MEAL_8, MEAL_7, MEAL_6);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime start = LocalDateTime.of(2018, Month.JULY, 15, 10, 0, 0);
        LocalDateTime end = LocalDateTime.of(2018, Month.JULY, 15, 14, 0, 0);
        assertMatch(service.getBetweenDateTimes(start, end, USER_ID), MEAL_4);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(ADMIN_ID), MEAL_2, MEAL_1, MEAL_0);
    }

    @Test
    public void update() {
        Meal oldMeal = service.get(MEAL_0.getId(), ADMIN_ID);
        Meal newMeal = new Meal(oldMeal);
        newMeal.setDescription("new description");
        service.update(newMeal, ADMIN_ID);
        assertMatch(service.get(MEAL_0.getId(), ADMIN_ID), newMeal);
    }

    @Test
    public void create() {
        LocalDateTime now = LocalDateTime.now();
        Meal newMeal = new Meal(null, now, "Description", 500);
        Meal createdMeal = service.create(newMeal, USER_ID);
        assertMatch(service.get(createdMeal.getId(), USER_ID), createdMeal);
    }

    @Test(expected = NotFoundException.class)
    public void getForeign() {
        service.get(MEAL_0.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteForeign() {
        service.delete(MEAL_0.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeign() {
        Meal meal = service.get(MEAL_0.getId(), ADMIN_ID);
        service.update(meal, USER_ID);
    }
}