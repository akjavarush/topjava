package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealServiceImpl implements UserMealService {

    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal userMeal) {
        return repository.save(userMeal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        repository.delete(id);
    }

    @Override
    public UserMeal get(int id) throws NotFoundException {
        return repository.get(id);
    }

    @Override
    public List<UserMealWithExceed> getByTimeFrame(LocalTime startTime, LocalTime endTime, int caloriesPerDay) throws NotFoundException {
        return UserMealsUtil.getFilteredWithExceeded(repository.getAll(), startTime, endTime, caloriesPerDay);
    }

    @Override
    public List<UserMealWithExceed> getAll(int caloriesPerDay) {
        Collection<UserMeal> userMeals = (List<UserMeal>)repository.getAll();

        return UserMealsUtil.getWithExceeded(userMeals, caloriesPerDay);
    }

    @Override
    public void update(UserMeal userMeal) {
        repository.save(userMeal);
    }
}
