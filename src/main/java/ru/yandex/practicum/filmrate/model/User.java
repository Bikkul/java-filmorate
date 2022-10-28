package ru.yandex.practicum.filmrate.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;
    @Email
    private String email;
    @NonNull
    @NotBlank
    @NotEmpty
    private String login;
    private String name;
    @NonNull
    private LocalDate birthday;
}
