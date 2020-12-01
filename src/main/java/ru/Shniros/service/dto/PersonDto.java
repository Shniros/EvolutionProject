package ru.Shniros.service.dto;

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
