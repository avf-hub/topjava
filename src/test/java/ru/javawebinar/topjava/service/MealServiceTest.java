package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.lang.Nullable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.net.ConnectException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
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
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID7, ID_NOT_FOUND));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID2, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID2, USER_ID));
    }

    @Test
    public void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID8, ID_NOT_FOUND));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filtered = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        assertMatch(filtered, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getBetweenInclusiveNotFound() {
        assertThrows(NullPointerException.class, () -> service.getBetweenInclusive(null, null, USER_ID));
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
                ADMIN_ID
        ));
    }
}