package ru.graduation.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "dish", uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "dish_name"},
        name = "restaurant_dish_idx"))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Dish extends BaseEntity {
    @Column(name = "dish_name")
    @NotEmpty
    @Size(max = 256)
    private String dishName;

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    @Column(name = "registered", columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    private Date registered = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @NotNull
    private Restaurant restaurant;
}
