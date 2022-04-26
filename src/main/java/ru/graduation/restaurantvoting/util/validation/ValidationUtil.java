package ru.graduation.restaurantvoting.util.validation;

import lombok.experimental.UtilityClass;
import ru.graduation.restaurantvoting.HasId;
import ru.graduation.restaurantvoting.exception.IllegalRequestDataException;
import ru.graduation.restaurantvoting.model.VotingResult;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class ValidationUtil {

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

    public static boolean checkVotingDate(VotingResult votingResult) {
        final LocalDate now = LocalDate.now();
        final LocalDateTime checkTime =
                LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), 11, 0);
        return votingResult.getRegistered().isAfter(checkTime);
    }
}