package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.InMemoryMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealStorage storage;

    @Override
    public void init() throws ServletException {
        storage = new InMemoryMealStorage();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            log.debug("no action, forward to meals");
            req.setAttribute("meals", MealsUtil.getMealTos(storage.getAll()));
            req.getRequestDispatcher("mealsList.jsp").forward(req, resp);
            return;
        }
        switch (action) {
            case "delete":
                log.debug("action \"delete\", redirect to meals");
                storage.delete(getId(req));
                resp.sendRedirect("meals");
                return;
            case "add":
            case "edit":
                log.debug("action \"edit\" and \"add\", forward to meals");
                final Meal meal = "add".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 0) :
                        storage.get(getId(req));
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
                break;
        }
    }

    private Integer getId(HttpServletRequest req) {
        String paramId = Objects.requireNonNull(req.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));
        log.info(meal.isNew() ? "create new meal, redirect ro meals" : "update meal, redirect ro meals");
        storage.save(meal);
        resp.sendRedirect("meals");
    }
}
