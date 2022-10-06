package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID1 = START_SEQ + 3;
    public static final int USER_MEAL_ID2 = START_SEQ + 4;
    public static final int USER_MEAL_ID3 = START_SEQ + 5;
    public static final int USER_MEAL_ID4 = START_SEQ + 6;
    public static final int USER_MEAL_ID5 = START_SEQ + 7;
    public static final int USER_MEAL_ID6 = START_SEQ + 8;
    public static final int USER_MEAL_ID7 = START_SEQ + 9;
    public static final int USER_MEAL_ID8 = START_SEQ + 10;
    public static final int ADMIN_MEAL_ID1 = START_SEQ + 11;
    public static final int ADMIN_MEAL_ID2 = START_SEQ + 12;
    public static final int ADMIN_MEAL_ID3 = START_SEQ + 13;
    public static final int NEW_MEAL_ID = START_SEQ + 14;
    public static final int MEAL_ID_NOT_FOUND = 10;

    public static final Meal userMeal1 = new Meal(USER_MEAL_ID1,
            LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(USER_MEAL_ID2,
            LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(USER_MEAL_ID3,
            LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(USER_MEAL_ID4,
            LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(USER_MEAL_ID5,
            LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(USER_MEAL_ID6,
            LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(USER_MEAL_ID7,
            LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal userMeal8 = new Meal(USER_MEAL_ID8,
            LocalDateTime.of(2022, Month.OCTOBER, 2, 11, 0), "Завтрак вместе с админом", 710);
    public static final Meal adminMeal1 = new Meal(ADMIN_MEAL_ID1,
            LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal adminMeal2 = new Meal(ADMIN_MEAL_ID2,
            LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ланч", 1500);
    public static final Meal adminMeal3 = new Meal(ADMIN_MEAL_ID3,
            LocalDateTime.of(2022, Month.OCTOBER, 2, 11, 0), "Завтрак вместе с пользователем", 710);
    public static final Meal mealNotFound = new Meal(MEAL_ID_NOT_FOUND,
            LocalDateTime.of(2022, Month.OCTOBER, 7, 11, 0), "Завтрак", 210);

    public static Meal getNew() {
        return new Meal(NEW_MEAL_ID, LocalDateTime.of(2022, Month.OCTOBER, 1, 21, 0), "new meal", 1234);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal();
        updated.setId(USER_MEAL_ID7);
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
