package ru.graduation.restaurantvoting.util.validation;

import lombok.experimental.UtilityClass;
import ru.graduation.restaurantvoting.HasId;
import ru.graduation.restaurantvoting.exception.IllegalRequestDataException;
import ru.graduation.restaurantvoting.model.Restaurant;
import ru.graduation.restaurantvoting.to.DishTo;

import java.time.LocalTime;

@UtilityClass
public class ValidationUtil {

    private static final LocalTime VOTING_END_TIME = LocalTime.parse("11:00");

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new IllegalRequestDataException("Entity with id=" + id + " not found");
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id=" + id);
        }
    }

    public static boolean checkVotingTime(LocalTime now) {
        return now.isAfter(VOTING_END_TIME);
    }


    public static void checkDish(DishTo dish) {
        if (dish.getRestaurantId() == null) {
            throw new IllegalRequestDataException("Restaurant must be with (id!=null)");
        }
    }
}