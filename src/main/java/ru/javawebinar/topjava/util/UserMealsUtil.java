package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(14,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field

        Map<LocalDate, Integer> exceedsMap = new HashMap<>();
        List<UserMealWithExceed> retList = new ArrayList<>();

        List<UserMeal> filteredMeals = mealList.stream().
                filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime)).
                collect(Collectors.toList());

        mealList.stream().
                forEach( meal-> {
                        LocalDate mealDate = meal.getDateTime().toLocalDate();
                        Integer totalCalories;
                        if (exceedsMap.containsKey(mealDate))
                        {
                            totalCalories = exceedsMap.get(mealDate) + meal.getCalories();
                        } else
                        {
                            totalCalories = meal.getCalories();
                        }

                        exceedsMap.put(mealDate, totalCalories);
                }
        );

       filteredMeals.stream().
                forEach(meal ->
                retList.add( new UserMealWithExceed(meal.getDateTime(),
                                                    meal.getDescription(),
                                                    meal.getCalories(),
                                                    exceedsMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
        );

        System.out.println("******* Return Array List ******");

        retList.stream().
                peek(System.out::println).collect(Collectors.toList());

        return retList;
    }
}
