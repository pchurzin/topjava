package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

public class MealTestData {
    public static final int START_ID = ADMIN_ID + 1;

    public static final Meal MEAL_0 = new Meal(START_ID + 0, LocalDateTime.of(2018, Month.JULY, 14, 8, 00, 00), "Завтрак админа", 500);
    public static final Meal MEAL_1 = new Meal(START_ID + 1, LocalDateTime.of(2018, Month.JULY, 14, 13, 00, 00), "Обед админа", 1000);
    public static final Meal MEAL_2 = new Meal(START_ID + 2, LocalDateTime.of(2018, Month.JULY, 14, 20, 00, 00), "Ужин админа", 500);
    public static final Meal MEAL_3 = new Meal(START_ID + 3, LocalDateTime.of(2018, Month.JULY, 15, 8, 00, 00), "Завтрак 2", 500);
    public static final Meal MEAL_4 = new Meal(START_ID + 4, LocalDateTime.of(2018, Month.JULY, 15, 13, 00, 00), "Обед 2", 1000);
    public static final Meal MEAL_5 = new Meal(START_ID + 5, LocalDateTime.of(2018, Month.JULY, 15, 20, 00, 00), "Ужин 2", 500);
    public static final Meal MEAL_6 = new Meal(START_ID + 6, LocalDateTime.of(2018, Month.JULY, 14, 8, 00, 00), "Завтрак 1", 500);
    public static final Meal MEAL_7 = new Meal(START_ID + 7, LocalDateTime.of(2018, Month.JULY, 14, 13, 00, 00), "Обед 1", 1000);
    public static final Meal MEAL_8 = new Meal(START_ID + 8, LocalDateTime.of(2018, Month.JULY, 14, 20, 00, 00), "Ужин 1", 510);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
