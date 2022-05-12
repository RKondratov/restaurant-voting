package ru.graduation.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.graduation.restaurantvoting.model.Dish;

import java.util.Date;
import java.util.List;

public interface DishRepository extends BaseRepository<Dish> {
    @Transactional(readOnly = true)
    @Query("Select d from Dish d where d.restaurant.id = :restaurantId " +
            "and d.creationDate >= :startDate and d.creationDate <= :endDate")
    List<Dish> findAllByRestaurantIdAndCreationDate(@Param("restaurantId") int restaurantId,
                                                    @Param("startDate") Date startDate,
                                                    @Param("endDate") Date endDate);

    @Transactional(readOnly = true)
    @Query("Select d from Dish d where d.creationDate >= :startDate and d.creationDate <= :endDate")
    List<Dish> findAllByCreationDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}