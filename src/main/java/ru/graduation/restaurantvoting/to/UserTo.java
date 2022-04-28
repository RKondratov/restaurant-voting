package ru.graduation.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.graduation.restaurantvoting.HasIdAndEmail;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@EqualsAndHashCode(callSuper = true)
public class UserTo extends BaseTo implements HasIdAndEmail {

    @NotBlank
    @Size(min = 1, max = 128)
    String firstName;

    @NotBlank
    @Size(min = 1, max = 128)
    String lastName;

    @Email
    @NotBlank
    @Size(max = 100)
    String email;

    @NotBlank
    @Size(min = 5, max = 32)
    String password;

    public UserTo(Integer id, String firstName, String lastName, String email, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + firstName + ']' + '[' + lastName + ']';
    }
}