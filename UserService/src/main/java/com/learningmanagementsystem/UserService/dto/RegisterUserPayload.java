package com.learningmanagementsystem.UserService.dto;


import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterUserPayload {

    @NotNull
    @NotBlank
    @Size(min =3, max = 60)
    private  String username;

    @NotNull
    @NotBlank
    @Size(min =3, max = 60)
    private String name;

    @NotNull
    @NotBlank
    @Email
    @Size(max = 255)
    private String email;

    @NotNull
    @NotBlank
    @Pattern(regexp =  "^\\(?(\\d{4})\\)?[- ]?(\\d{3})[- ]?(\\d{3})[- ]?(\\d{3})$", message = "Invalid telephone")
    private String telephone;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 255)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 255)
    private String confirmPassword;

    @NotNull
    @NotBlank
    private String role;

}
