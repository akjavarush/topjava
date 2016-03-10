package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.exceptions.EntryNotFoundException;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by propant on 09.03.2016.
 */
public class MealDaoMapImpl implements MealDao {

    private static Map<Integer, UserMeal> meals = new ConcurrentHashMap<>();
    {
        UserMeal meal1 =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        meals.put(meal1.getId(), meal1);

        UserMeal meal2 =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
        meals.put(meal2.getId(), meal2);

        UserMeal meal3 =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
        meals.put(meal3.getId(), meal3);

        UserMeal meal4 =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
        meals.put(meal4.getId(), meal4);

        UserMeal meal5 =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
        meals.put(meal5.getId(), meal5);

        UserMeal meal6 =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
        meals.put(meal6.getId(), meal6);

    }

    @Override
    public void saveMeal(UserMeal meal) {

        meals.put(meal.getId(), meal);

    }

    @Override
    public void deleteMealById(String id) throws EntryNotFoundException {
        try{
            meals.remove(Integer.parseInt(id));
        } catch (NumberFormatException e)
        {
            throw new EntryNotFoundException();
        }
    }

    @Override
    public UserMeal findMealById(String id) throws EntryNotFoundException {

        UserMeal meal = null;

        try{
            meal = meals.get(Integer.parseInt(id));
        } catch (NumberFormatException e)
        {
            throw new EntryNotFoundException();
        }
        return meal;
    }

    @Override
    public List<UserMeal> getAllMeals() {

        List<UserMeal> mealList = new ArrayList<>();
        for (Map.Entry<Integer, UserMeal> meal : meals.entrySet())
            mealList.add(meal.getValue());


        return mealList;
    }

    public Map<Integer, UserMeal> getAllMealsMap(){
        return meals;
    }
}
