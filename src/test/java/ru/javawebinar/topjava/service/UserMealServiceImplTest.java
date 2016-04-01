package ru.javawebinar.topjava.service;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.taglibs.standard.tag.el.sql.SetDataSourceTag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

import static ru.javawebinar.topjava.MealTestData.*;

/**
 * Created by propant on 31.03.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceImplTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;


    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();


        System.out.println(USER_MEALS);
        System.out.println(ADMIN_MEALS);
    }

    @Test
    public void get() throws Exception {

        UserMeal dbUserMeal = service.get(2, USER_ID);
        UserMeal ListUserMeal =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);

        MATCHER.assertEquals(ListUserMeal, dbUserMeal);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUserException() throws Exception {
        UserMeal dbUserMeal = service.get(2, 1000);
        UserMeal ListUserMeal =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);


    }

    @Test(expected = NotFoundException.class)
    public void getWrongMealIDException() throws Exception {
        UserMeal dbUserMeal = service.get(52, USER_ID);
        UserMeal ListUserMeal =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);

    }


    @Test
    public void delete() throws Exception {

        int dbSize = service.getAll(USER_ID).size();
        service.delete(2, USER_ID);

        int newDbSize = service.getAll(USER_ID).size();
        Assert.assertEquals(dbSize-1, newDbSize);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {

        LocalDateTime startDate = LocalDateTime.of(2015, Month.MAY, 30, 10, 00);
        LocalDateTime endDate = LocalDateTime.of(2015, Month.MAY, 31, 15, 00);

        List<UserMeal> expectedMeals = USER_MEALS.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDate, endDate)).sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());

        List<UserMeal> actualMeals = (List<UserMeal>) service.getBetweenDateTimes(startDate, endDate, USER_ID);

        Assert.assertEquals(expectedMeals.toString().trim(), actualMeals.toString().trim());
    }

    @Test
    public void getAll() throws Exception {
        List<UserMeal> expectedMeals = USER_MEALS.stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());

        List<UserMeal> actualMeals = (List<UserMeal>) service.getAll(USER_ID);

        Assert.assertEquals(expectedMeals.toString().trim(), actualMeals.toString().trim());
    }
    @Test
    public void getAllWrongUser() throws Exception {
        List<UserMeal> expectedMeals = USER_MEALS.stream()
                .sorted((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()))
                .collect(Collectors.toList());

        List<UserMeal> actualMeals = (List<UserMeal>) service.getAll(10);

        Assert.assertEquals(0, actualMeals.size());
    }

    @Test
    public void save() throws Exception {

        UserMeal mealToAdd =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "перекус", 500);

        List<UserMeal> expectedList = new ArrayList<>(USER_MEALS);
        expectedList.add(mealToAdd);
        service.update(mealToAdd, USER_ID);

        List<UserMeal> expectedMeals = expectedList.stream().sorted((o1,o2)->o2.getDateTime().compareTo(o1.getDateTime())).collect(Collectors.toList());

        Assert.assertEquals(expectedMeals.toString(), service.getAll(USER_ID).toString());

    }

    @Test(expected = NotFoundException.class)
    public void saveWrongUser() throws Exception {

        UserMeal mealToAdd =  new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "перекус", 500);
        service.update(mealToAdd, 100);

    }

    @Test
    public void update() throws Exception {

        UserMeal userMeal = service.get(2, USER_ID);
        userMeal.setDescription("Крепкий перекус");
        userMeal.setCalories(5000);

        service.save(userMeal, USER_ID);

        MATCHER.assertEquals(service.get(2, USER_ID), userMeal);

    }
}