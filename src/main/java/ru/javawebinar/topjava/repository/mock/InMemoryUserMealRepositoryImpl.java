package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);

    {
        LOG.info("Fulfilling MEAL_LIST");
        UserMealsUtil.MEAL_LIST.forEach(this::save);
        UserMealsUtil.MEAL_LIST.forEach(this::updateUser);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        /*LOG.info("Save UserMeal " + userMeal + " InMemoryRepository");*/
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        LOG.info("Save UserMeal " + userMeal + " InMemoryRepository");

        return userMeal;
    }


    public UserMeal updateUser(UserMeal userMeal) {

        LOG.info("Setting UserIDs to UserMeal " + userMeal + " InMemoryRepository");

        if ((userMeal.getUserId()== null) && (userMeal.getId() != 3) && (userMeal.getId() != 5)) {
            userMeal.setUserId(1);
        } else
        {
            userMeal.setUserId(2);
        }

        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }
    @Override
    public boolean delete(int id) {

        LOG.info("Deleting UserMeal with id " + id);

        if (LoggedUser.id() == repository.get(id).getUserId())
        {
            repository.remove(id);
            return true;
        }

        return false;
    }

    @Override
    public UserMeal get(int id) {
        LOG.info("Getting UserMeal with id " + id);
        if (LoggedUser.id() == repository.get(id).getUserId())
        {
            repository.get(id);

        }

        return null;
    }

    @Override
    public Collection<UserMeal> getAll() {
        LOG.info("Getting ALL UserMeals filtered by UserID=1 and Sorted by DateTime!" );

        return repository.values()
                .stream()
                .filter(um->((um.getUserId()!= null)&&(um.getUserId().intValue() == LoggedUser.id())))
                .sorted((um1, um2)->um2.getDateTime().compareTo(um1.getDateTime()))
                .collect(Collectors.toList());
    }
}

