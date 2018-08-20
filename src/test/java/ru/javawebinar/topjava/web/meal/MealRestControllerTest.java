package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;
import static ru.javawebinar.topjava.UserTestData.USER;

class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + "/";


    @Autowired
    private MealService mealService;

    @Test
    void testGetAll() throws Exception {
        SecurityUtil.setAuthUserId(USER.getId());
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExceeded(MEALS, SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void testGet() throws Exception {
        SecurityUtil.setAuthUserId(USER.getId());
        mockMvc.perform(get(REST_URL + MEAL1.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void testCreateWithLocation() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN.getId());
        Meal expected = new Meal(LocalDateTime.now(), "new meal", 777);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated());

        Meal returned = TestUtil.readFromJson(action, Meal.class);
        expected.setId(returned.getId());

        assertMatch(returned, expected);
        assertMatch(mealService.getAll(SecurityUtil.authUserId()), expected, ADMIN_MEAL2, ADMIN_MEAL1);
    }

    @Test
    void testDelete() throws Exception {
        SecurityUtil.setAuthUserId(ADMIN.getId());
        mockMvc.perform(delete(REST_URL + ADMIN_MEAL1.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertMatch(mealService.getAll(ADMIN.getId()), Arrays.asList(ADMIN_MEAL2));
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL6);
        updated.setDescription("new description");
        mockMvc.perform(put(REST_URL + updated.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        assertMatch(mealService.get(updated.getId(), SecurityUtil.authUserId()), updated);
    }

    @Test
    void testGetBetween() throws Exception {
        SecurityUtil.setAuthUserId(USER.getId());
        mockMvc.perform(get(REST_URL).param("filter", ""))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil
                        .getFilteredWithExceeded(
                                MEALS,
                                SecurityUtil.authUserCaloriesPerDay(),
                                null, null, null, null)));

        String START_DATE = "2015-05-31";
        mockMvc.perform(get(REST_URL)
                .param("filter", "")
                .param("startDate", START_DATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil
                        .getFilteredWithExceeded(
                                MEALS,
                                SecurityUtil.authUserCaloriesPerDay(),
                                LocalDate.parse(START_DATE), null, null, null)));

        String END_DATE = "2015-05-30";
        mockMvc.perform(get(REST_URL)
                .param("filter", "")
                .param("endDate", END_DATE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil
                        .getFilteredWithExceeded(MEALS,
                                SecurityUtil.authUserCaloriesPerDay(),
                                null, LocalDate.parse(END_DATE), null, null)));

        String START_TIME = "20:00";
        mockMvc.perform(get(REST_URL)
                .param("filter", "")
                .param("startTime", START_TIME))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil
                        .getFilteredWithExceeded(
                                MEALS,
                                SecurityUtil.authUserCaloriesPerDay(),
                                null, null, LocalTime.parse(START_TIME), null)));

        String END_TIME = "11:00";
        mockMvc.perform(get(REST_URL)
                .param("filter", "")
                .param("endTime", "11:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil
                        .getFilteredWithExceeded(
                                MEALS,
                                SecurityUtil.authUserCaloriesPerDay(),
                                null, null, null, LocalTime.parse(END_TIME))));

        String DATE = "2015-05-31";
        String START_TIME2 = "11:00";
        String END_TIME2 = "14:00";
        mockMvc.perform(get(REST_URL)
                .param("filter", "")
                .param("startDate", DATE)
                .param("endDate", DATE)
                .param("startTime", START_TIME2)
                .param("endTime", END_TIME2))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil
                        .getFilteredWithExceeded(
                                MEALS,
                                SecurityUtil.authUserCaloriesPerDay(),
                                LocalDate.parse(DATE), LocalDate.parse(DATE), LocalTime.parse(START_TIME2), LocalTime.parse(END_TIME2))));
    }
}