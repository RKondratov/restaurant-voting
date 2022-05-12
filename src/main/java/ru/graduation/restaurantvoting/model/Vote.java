package ru.graduation.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "registered"}, name = "vote_idx"))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Vote extends BaseEntity {
    @Column(name = "registered", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime registered;

    @Column(name = "restaurant_id", nullable = false)
    private Integer restaurantId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id",
            insertable = false,
            updatable = false,
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @Schema(hidden = true)
    private Restaurant restaurant;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",
            insertable = false,
            updatable = false,
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    @Schema(hidden = true)
    private User user;

    public Vote(LocalDateTime registered, Integer restaurantId, Integer userId) {
        this.registered = registered;
        this.restaurantId = restaurantId;
        this.userId = userId;
    }
}