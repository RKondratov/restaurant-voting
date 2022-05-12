package ru.graduation.restaurantvoting.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Size(max = 128)
    private String name;

    public Restaurant(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
