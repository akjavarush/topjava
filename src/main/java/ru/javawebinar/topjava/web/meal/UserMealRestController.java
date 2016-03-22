package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.http.HttpServlet;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealRestController {

    @Autowired
    @Qualifier("userMealService")
    private UserMealService service;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    public List<UserMealWithExceed> getFilteredUserMeals(LocalTime startTime, LocalTime endTime, int caloriesPerDay)
    {
        return service.getByTimeFrame(startTime, endTime, caloriesPerDay, LoggedUser.id());
    }

    public List<UserMealWithExceed> getAll(int caloriesPerDay){
        return service.getAll(caloriesPerDay, LoggedUser.id());
    }

    public void delete(int id){
        service.delete(id, LoggedUser.id());
    }
    public UserMeal get(int id)
    {
        return service.get(id, LoggedUser.id());
    }

    public void save(UserMeal userMeal){
        service.save(userMeal, LoggedUser.id());
    }
}
