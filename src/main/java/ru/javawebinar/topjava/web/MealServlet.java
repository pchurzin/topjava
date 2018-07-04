package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceMemory;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    public static final int CALORIES_PER_DAY = 2000;
    public static final String PARAM_ID = "id";
    private static final Logger log = getLogger(MealServlet.class);
    private static final MealService mealService = new MealServiceMemory();
    private static final DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameterMap().containsKey(PARAM_ID)) {
            Meal meal = mealService.getById(Long.parseLong(request.getParameter(PARAM_ID)));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/meal_form.jsp").forward(request, response);
        }

        List<Meal> meals = mealService.getAll();
        List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("meals", mealsWithExceed);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if ("delete".equalsIgnoreCase(action)) {
            doDelete(req, resp);
        } else if ("update".equalsIgnoreCase(action)) {
            doPut(req, resp);
        } else if ("create".equalsIgnoreCase(action)) {
            mealService.add(getMealFromRequest(req));
        }
        resp.sendRedirect("/topjava/meals");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mealService.remove(Long.valueOf(req.getParameter(PARAM_ID)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Meal meal = getMealFromRequest(req);
        mealService.update(Long.valueOf(req.getParameter(PARAM_ID)), meal);
    }

    private Meal getMealFromRequest(HttpServletRequest req) {
        return new Meal(
                LocalDateTime.parse(req.getParameter("date") + " " + req.getParameter("time"), formater),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
    }
}