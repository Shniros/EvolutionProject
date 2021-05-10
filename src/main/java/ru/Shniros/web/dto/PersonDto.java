package ru.Shniros.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PersonDto {
    private String firstName;
    private String lastName;
    private String email;
    private int id;
}
