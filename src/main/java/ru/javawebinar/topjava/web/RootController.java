package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.RequestWrapper;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
@Controller
public class RootController {
    @Autowired
    private UserService service;

    @Autowired
    private UserMealRestController mealController;

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root() {
        return "index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String userList(Model model) {
        model.addAttribute("userList", service.getAll());
        return "userList";
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String setUser(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userId"));
        LoggedUser.setId(userId);
        return "redirect:meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "!action")
    public String mealsList(Model model, HttpServletRequest request) {
        model.addAttribute("mealList", mealController.getAll());
        return "mealList";
    }


    @RequestMapping(value = "/meals", method = RequestMethod.GET, params="action=delete")
    public String mealsDelete(@RequestParam("id") Integer id) {

            mealController.delete(id);
            return "redirect:meals";

    }

    /*if ("delete".equals(action)) {
        mealController.delete(id);
        return new ModelAndView("redirect:meals");
    } else {
        final UserMeal meal = "create".equals(action) ?
                new UserMeal(LocalDateTime.now(), "", 1000) :   // create
                mealController.get(id);             // update
        request.setAttribute("mealForm", meal);
        return new ModelAndView("mealEdit", "mealForm", meal);
    */

    @RequestMapping(value = "/meals", method = RequestMethod.GET, params = "action=update")
    public String mealsEdit( Model model,
                                  @RequestParam(value = "id", required = false) Integer id) {

            UserMeal meal = mealController.get(id);             // update
            model.addAttribute("mealForm", meal);
            return "mealEdit";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String mealsEdit( @ModelAttribute("mealForm") UserMeal userMeal, Model model) {

        mealController.update(userMeal, userMeal.getId());
        model.addAttribute("mealList", mealController.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST, params = "action=filter")
    public String filterMeals(HttpServletRequest request){
            LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
            LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
            LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
            LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
            request.setAttribute("mealList", mealController.getBetween(startDate, startTime, endDate, endTime));
            return "mealList";
    }

   /* @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String editMeal(*//*@ModelAttribute("userForm") UserMeal userMeal*//*
            Model model,
                           @RequestParam(value = "id", required = false) Integer id){

        return("mealList");

    }
*/
    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

}
