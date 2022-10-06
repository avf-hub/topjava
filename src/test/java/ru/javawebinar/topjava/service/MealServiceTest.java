package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db-jdbc.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID1, USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_NOT_FOUND, USER_ID));
    }

    @Test
    public void getEveryoneMeal() {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_ID2, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID2, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID2, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID_NOT_FOUND, USER_ID));
    }

    @Test
    public void deletedEveryoneMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL_ID3, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filtered = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        assertMatch(filtered, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getBetweenInclusiveNoDate() {
        List<Meal> meals = service.getBetweenInclusive(null, null, ADMIN_ID);
        assertMatch(meals, adminMeal3, getNew(), adminMeal2, adminMeal1);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, userMeal8, userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_ID7, USER_ID), getUpdated());
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(mealNotFound, USER_ID));
    }

    @Test
    public void updateEveryoneMeal() {
        assertThrows(NotFoundException.class, () -> service.update(adminMeal1, USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDataTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(
                new Meal(userMeal6.getDateTime(), "Duplicate", 1200),
                USER_ID
        ));
    }
}