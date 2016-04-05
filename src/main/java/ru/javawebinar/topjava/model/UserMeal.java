package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */

@Entity
@Table(name = "meals")
@NamedQueries({
        @NamedQuery(
                name= UserMeal.GET,
                query="SELECT m FROM UserMeal m WHERE m.id =:id AND m.user.id=:userId"
        ),

        @NamedQuery(
                name= UserMeal.GET_ALL,
                query="SELECT m FROM UserMeal m WHERE m.user.id =:userId ORDER BY m.dateTime DESC "
        ),
        @NamedQuery(
                name= UserMeal.DELETE,
                query="DELETE FROM UserMeal m WHERE m.user.id =:userId AND m.id=:id"
        ),
        @NamedQuery(
                name= UserMeal.GET_BETWEEN,
                query="SELECT m FROM UserMeal m WHERE m.dateTime >=:startDate AND m.dateTime <=:endDate AND m.user.id =:userId ORDER BY m.dateTime DESC"
        )
})
public class UserMeal extends BaseEntity {

    public static final String GET = "Meal.get";
    public static final String GET_ALL = "Meal.getAll";
    public static final String DELETE = "Meal.delete";
    public static final String GET_BETWEEN = "Meal.save";

    @Column(name = "date_time", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    protected int calories;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
