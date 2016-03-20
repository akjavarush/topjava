package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealRestController {
    private UserMealService service;
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    List<UserMealWithExceed> getFilteredUserMeals(LocalTime startTime, LocalTime endTime, int caloriesPerDay)
    {
        return service.getByTimeFrame(startTime, endTime, caloriesPerDay);
    }

    List<UserMealWithExceed> getAll(){
        return service.getAll(UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    UserMeal get(int id)
    {

        return service.get(id);
    }
}
