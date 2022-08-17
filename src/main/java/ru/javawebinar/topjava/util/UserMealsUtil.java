package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDay = new HashMap<>();
        for (UserMeal element : meals) {
            LocalDate mealDay = element.getDateTime().toLocalDate();
            caloriesSumPerDay.put(mealDay, caloriesSumPerDay.getOrDefault(mealDay, 0) + element.getCalories());
        }

        List<UserMealWithExcess> filterMeals = new ArrayList<>();
        for (UserMeal element : meals) {
            if (TimeUtil.isBetweenHalfOpen(element.getDateTime().toLocalTime(), startTime, endTime)) {
                filterMeals.add(new UserMealWithExcess(element.getDateTime(), element.getDescription(), element.getCalories(), caloriesSumPerDay.get(element.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return filterMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumPerDay = meals.stream().collect(Collectors.groupingBy(el -> el.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return meals.stream()
                .filter(el -> TimeUtil.isBetweenHalfOpen(el.getDateTime().toLocalTime(), startTime, endTime))
                .map(el -> new UserMealWithExcess(el.getDateTime(), el.getDescription(), el.getCalories(), caloriesSumPerDay.get(el.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
