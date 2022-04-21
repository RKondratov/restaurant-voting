package ru.graduation.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.graduation.restaurantvoting.model.Meal;

import java.util.List;

public interface MealRepository extends BaseRepository<Meal> {
    @Query("Select m from Meal m where m.restaurant.id = :restaurantId")
    List<Meal> findAllByRestaurantId(@Param("restaurantId") int restaurantId);
}
