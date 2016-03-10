package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.exceptions.EntryNotFoundException;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

/**
 * Created by propant on 09.03.2016.
 */
public interface MealDao {

    void saveMeal(UserMeal meal);

    void deleteMealById(String id) throws EntryNotFoundException;

    UserMeal findMealById(String id) throws EntryNotFoundException;

    List<UserMeal> getAllMeals();
}
