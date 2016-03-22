package ru.javawebinar.topjava.service;

import org.springframework.cglib.core.Local;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal save(UserMeal userMeal, int userId);

    void delete(int id, int userId) throws NotFoundException;

    UserMeal get(int id, int userId) throws NotFoundException;

    List<UserMealWithExceed> getByTimeFrame(LocalTime startTime, LocalTime endTime, int caloriesPerDay, int userId) throws NotFoundException;

    List<UserMealWithExceed> getByDateTimeFrame(LocalDateTime startTime, LocalDateTime endTime, int caloriesPerDay, int userId) throws NotFoundException;

    List<UserMealWithExceed> getAll(int caloriesPerDay, int userId);

    void update(UserMeal userMeal, int userId);
}
