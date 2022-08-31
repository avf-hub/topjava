package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private final List<Meal> meals = new ArrayList<>();
    private final List<MealTo> mealsTo = new ArrayList<>();
    private static final int CALORIES_PER_DAY = 2000;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("meals", meals.stream()
                    .map(meal -> new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumByDate.get(meal.getDate()) > CALORIES_PER_DAY))
                    .collect(Collectors.toList()));
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
            return;
        }
        MealTo mealTo = null;
        switch (action) {
            case "delete":
                int index = Integer.parseInt(req.getParameter("index"));
                mealsTo.remove(index);
                meals.remove(index);
                resp.sendRedirect("meals");
                return;
            case "add":
                mealTo = new MealTo(LocalDateTime.now(), "", 0, false);
                break;
            case "edit":
                mealTo = mealsTo.get(Integer.parseInt(req.getParameter("index")));
                break;
        }

        req.setAttribute("meals", mealTo);
        req.getRequestDispatcher("editmeal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        AtomicReference<Boolean> isNew = new AtomicReference<>(true);
        meals.forEach(m -> {
            if (dateTime.equals(m.getDateTime()) && description.equals(m.getDescription()) && calories==m.getCalories()) {
                isNew.set(false);
            }
                });
        if (isNew.get()){
            Meal meal = new Meal(dateTime, description, calories);
            meals.add(meal);
            Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                    .collect(
                            Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                    );
            mealsTo.add(new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                    caloriesSumByDate.get(meal.getDate()) > CALORIES_PER_DAY));
        }
        resp.sendRedirect("meals");
    }
}
