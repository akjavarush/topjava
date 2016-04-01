package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    private static final RowMapper<UserMeal> ROW_MAPPER = new RowMapper<UserMeal>() {
        public UserMeal mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserMeal userMeal = new UserMeal();
            userMeal.setDateTimeSQL(rs.getTimestamp("date"));
            userMeal.setCalories(rs.getInt("calories"));
            userMeal.setDescription(rs.getString("description"));
            userMeal.setId(rs.getInt("meal_id"));
            return userMeal;
        }
    };

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUserMeal;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUserMeal =
                new SimpleJdbcInsert(dataSource)
                .withTableName("user_meals")
                .usingGeneratedKeyColumns("meal_id");
    }


    @Override
    public UserMeal save(UserMeal UserMeal, int userId) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("meal_id", UserMeal.getId())
                .addValue("date", UserMeal.getDateTime())
                .addValue("description", UserMeal.getDescription())
                .addValue("calories", UserMeal.getCalories())
                .addValue("user_id", userId);

        if (UserMeal.isNew()) {
            try
            {
                Number newKey = insertUserMeal.executeAndReturnKey(map);
                UserMeal.setId(newKey.intValue());
            } catch (Exception e)
            {
                throw new NotFoundException("User with ID " + userId + " not found");
            }


        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE user_meals SET date=:date, description=:description, calories=:calories, " +
                            "user_id=:user_id WHERE meal_id=:meal_id", map);
        }
        return UserMeal;
    }

    @Override
    public boolean delete(int id, int userId) {

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("meal_id", id)
                .addValue("userId", userId);
        int mealsDeleted = namedParameterJdbcTemplate.update(
                "DELETE FROM user_meals WHERE meal_id=:meal_id and user_id=:userId", map);

        if (mealsDeleted > 0)
            return true;
        else
            return false;
    }

    @Override
    public UserMeal get(int id, int userId) {

        Object[] args = {id, userId};
        List<UserMeal> mealsToGet = jdbcTemplate.query("SELECT meal_id, date, description, calories FROM user_meals WHERE meal_id=? and user_id=?",
               ROW_MAPPER, id, userId);

        return DataAccessUtils.singleResult(mealsToGet);
    }

    @Override
    public List<UserMeal> getAll(int userId) {

        List<UserMeal> retMeals =  jdbcTemplate.query("SELECT meal_id, date, description, calories FROM user_meals WHERE user_id=? ORDER BY date DESC ",
                ROW_MAPPER, userId);

        return retMeals;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        return getAll(userId).stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime(), startDate, endDate))
                .collect(Collectors.toList());
    }
}
