package ru.javawebinar.topjava.service;

import org.springframework.cglib.core.Local;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal save(UserMeal userMeal);

    void delete(int id) throws NotFoundException;

    UserMeal get(int id) throws NotFoundException;

    List<UserMealWithExceed> getByTimeFrame(LocalTime startTime, LocalTime endTime, int caloriesPerDay) throws NotFoundException;

    List<UserMealWithExceed> getAll(int caloriesPerDay);

    void update(UserMeal userMeal);
}
