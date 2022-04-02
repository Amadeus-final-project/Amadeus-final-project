package com.example.pds.model.dto.userDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserChangePasswordDTO {
    private String oldPass;
    private String newPass;
    private String confirmPass;
}
