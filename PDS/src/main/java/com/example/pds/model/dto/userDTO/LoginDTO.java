package com.example.pds.model.dto.userDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {

    @NotNull(message = "Please enter username")
    private String username;

    @NotNull(message = "Please enter password")
    private String password;
}
