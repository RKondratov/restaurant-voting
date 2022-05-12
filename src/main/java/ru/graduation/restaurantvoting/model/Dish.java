package ru.graduation.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "dish", uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "name"},
        name = "restaurant_dish_idx"))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Dish extends BaseEntity {
    @Column(name = "name")
    @NotBlank
    @Size(max = 256)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "creation_date", columnDefinition = "timestamp default now()", updatable = false, nullable = false)
    @NotNull
    private Date creationDate = new Date();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id",
            insertable = false,
            updatable = false,
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @Schema(hidden = true)
    private Restaurant restaurant;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    public Dish(String name, Integer price, Date creationDate, Integer restaurantId) {
        this.name = name;
        this.price = price;
        this.creationDate = creationDate;
        this.restaurantId = restaurantId;
    }
}
