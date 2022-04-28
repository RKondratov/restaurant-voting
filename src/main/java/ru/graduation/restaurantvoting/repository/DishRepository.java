package ru.graduation.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.graduation.restaurantvoting.model.Dish;

import java.util.List;

public interface DishRepository extends BaseRepository<Dish> {
    @Query("Select d from Dish d where d.restaurant.id = :restaurantId")
    List<Dish> findAllByRestaurantId(@Param("restaurantId") int restaurantId);
}
