package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.time.LocalTime;
import java.util.List;

/**
 * Created by propant on 20.03.2016.
 */
public interface MealController {
    List<UserMealWithExceed>  getFilteredUserMeals(LocalTime startTime, LocalTime endTime, int caloriesPerDay);

    List<UserMealWithExceed> getAll();

    UserMeal get(int id);
}
