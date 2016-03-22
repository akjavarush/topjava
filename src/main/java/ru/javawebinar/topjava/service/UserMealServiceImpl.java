package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryUserMealRepositoryImpl;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    @Qualifier("inMemoryUserMealRep")
    private UserMealRepository repository;


    private static final Logger LOG = LoggerFactory.getLogger(UserMealServiceImpl.class);

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        LOG.info("Saving userMeal " + userMeal);
        userMeal.setUserId(userId);
        return repository.save(userMeal);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        LOG.info("Deleting userMeal " + id);
        if (!repository.delete(id))
            throw new NotFoundException("Meal with ID = " + id + " does not belong to the User");

    }

    @Override
    public UserMeal get(int id, int userId) throws NotFoundException {

        LOG.info("Getting userMeal " + id);
        UserMeal gettingMeal = repository.get(id);

        if (gettingMeal != null)
            return gettingMeal;
        else
            throw new NotFoundException("Meal with ID = " + id + " does not belong the to User");
    }

    @Override
    public List<UserMealWithExceed> getByTimeFrame(LocalTime startTime, LocalTime endTime, int caloriesPerDay, int userId) throws NotFoundException {
        LOG.info("Fetching list of UserMealWithExceed within time frame!");
        return UserMealsUtil.getFilteredWithExceeded(repository.getAll(), startTime, endTime, caloriesPerDay);
    }

    @Override
    public List<UserMealWithExceed> getByDateTimeFrame(LocalDateTime startTime, LocalDateTime endTime, int caloriesPerDay, int userId) throws NotFoundException {
        return null;
    }

    @Override
    public List<UserMealWithExceed> getAll(int caloriesPerDay, int userId) {
        Collection<UserMeal> userMeals = repository.getAll();

        return UserMealsUtil.getWithExceeded(userMeals, caloriesPerDay);
    }

    @Override
    public void update(UserMeal userMeal, int userId) {
        repository.save(userMeal);
    }
}
