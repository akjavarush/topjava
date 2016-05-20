package ru.javawebinar.topjava.web.meal;

import org.springframework.http.MediaType;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
@RequestMapping(value = UserMealRestController.REST_URL)
public class UserMealRestController extends AbstractUserMealController {

    public static final String REST_URL = "/rest/meals";

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> mealList() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable("id") int id)
    {
        super.delete(id);
    }



/*
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String editForUpdate(HttpServletRequest request, Model model) {
        model.addAttribute("meal", super.get(getId(request)));
        return "mealEdit";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String editForCreate(Model model) {
        model.addAttribute("meal", new UserMeal(LocalDateTime.now(), "", 1000));
        return "mealEdit";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String updateOrCreate(HttpServletRequest request) {
        String id = request.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if (userMeal.isNew()) {
            super.create(userMeal);
        } else {
            super.update(userMeal, userMeal.getId());
        }
        return "redirect:/meals";
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = TimeUtil.parseLocalDate(resetParam("startDate", request));
        LocalDate endDate = TimeUtil.parseLocalDate(resetParam("endDate", request));
        LocalTime startTime = TimeUtil.parseLocalTime(resetParam("startTime", request));
        LocalTime endTime = TimeUtil.parseLocalTime(resetParam("endTime", request));
        model.addAttribute("mealList", super.getBetween(startDate, startTime, endDate, endTime));
        return "mealList";
    }

    private String resetParam(String param, HttpServletRequest request) {
        String value = request.getParameter(param);
        request.setAttribute(param, value);
        return value;
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }*/

}