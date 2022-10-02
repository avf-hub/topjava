package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_USER_ID1 = 100003;
    public static final int MEAL_USER_ID2 = 100004;
    public static final int MEAL_USER_ID3 = 100005;
    public static final int MEAL_USER_ID4 = 100006;
    public static final int MEAL_USER_ID5 = 100007;
    public static final int MEAL_USER_ID6 = 100008;
    public static final int MEAL_USER_ID7 = 100009;
    public static final int MEAL_ADMIN_ID1 = 100010;
    public static final int MEAL_ADMIN_ID2 = 100011;
    public static final int MEAL_NEW_ID = 100012;
    public static final int ID_NOT_FOUND = 10;

    public static final Meal user_meal1 = new Meal(MEAL_USER_ID1,
            LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal user_meal2 = new Meal(MEAL_USER_ID2,
            LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal user_meal3 = new Meal(MEAL_USER_ID3,
            LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal user_meal4 = new Meal(MEAL_USER_ID4,
            LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal user_meal5 = new Meal(MEAL_USER_ID5,
            LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal user_meal6 = new Meal(MEAL_USER_ID6,
            LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal user_meal7 = new Meal(MEAL_USER_ID7,
            LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal admin_meal1 = new Meal(MEAL_ADMIN_ID1,
            LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal admin_meal2 = new Meal(MEAL_ADMIN_ID2,
            LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ланч", 1500);

    public static Meal getNew() {
        return new Meal(MEAL_NEW_ID, LocalDateTime.of(2022, Month.OCTOBER, 1, 21, 0), "new meal", 1234);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal();
        updated.setId(MEAL_USER_ID7);
        updated.setDateTime(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0));
        updated.setDescription("Ужин");
        updated.setCalories(410);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
