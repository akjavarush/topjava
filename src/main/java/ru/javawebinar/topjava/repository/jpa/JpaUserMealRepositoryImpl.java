package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRep;


    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId) {

        User user = userRep.get(userId);
        userMeal.setUser(user);
        if (userMeal.isNew())
        {

            entityManager.persist(userMeal);
            System.out.println(userMeal);
            return userMeal;

        } else
        {

            UserMeal checkMeal = get(userMeal.getId(), userId);

            if (checkMeal != null)
                return entityManager.merge(userMeal);
            else
                return null;
        }


    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(UserMeal.DELETE).setParameter("userId", userId).setParameter("id", id).executeUpdate()==0 ? false : true;
    }

    @Override
    public UserMeal get(int id, int userId) {
        try
        {
            return entityManager.createNamedQuery(UserMeal.GET, UserMeal.class).setParameter("id",id).setParameter("userId", userId).getSingleResult();
        } catch (Exception e)
        {
            return null;
        }
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return entityManager.createNamedQuery(UserMeal.GET_ALL, UserMeal.class).setParameter("userId", userId).getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return entityManager.createNamedQuery(UserMeal.GET_BETWEEN, UserMeal.class).
                setParameter("startDate", startDate).
                setParameter("endDate", endDate).
                setParameter("userId", userId).
                getResultList();
    }
}