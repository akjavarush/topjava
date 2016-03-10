package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMapImpl;
import ru.javawebinar.topjava.exceptions.EntryNotFoundException;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by propant on 09.03.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private static MealDao mealsDao = new MealDaoMapImpl();
    private static UserMealsUtil mealsUtil = new UserMealsUtil();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        LOG.debug("Getting Meal List");

        List<UserMeal> meals = mealsDao.getAllMeals();
        List<UserMealWithExceed> mealWithExceeds = mealsUtil.getFilteredMealsWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, 2000);


        LOG.debug("Setting Meal List as request Attribute");
        req.setAttribute("meals", mealWithExceeds);

        LOG.info(req.getAttribute("meals").toString());

        LOG.debug("forward to mealList.jsp");
        getServletContext().getRequestDispatcher("/mealList.jsp").forward(req, resp);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String id = (String) request.getParameter("id");
        String dateTime = (String) request.getParameter("dateTime");
        String description = (String)request.getParameter("description");
        String calories = (String)request.getParameter("calories");

        try {
            UserMeal meal = mealsDao.findMealById(id);
            meal.setCalories(Integer.parseInt(calories));
            meal.setDescription(description);

            LOG.info("Save meal is " + meal.toString() );
            mealsDao.saveMeal(meal);

        } catch (EntryNotFoundException e) {
            e.printStackTrace();
        }

        LOG.debug("redirect to /topjava/meals");
        response.sendRedirect("/topjava/meals");
    }
}
