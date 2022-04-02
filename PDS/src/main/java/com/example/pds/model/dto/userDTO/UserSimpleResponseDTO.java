package com.example.pds.model.dto.userDTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSimpleResponseDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;

}
