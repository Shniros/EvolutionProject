package ru.Shniros.web.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@Data
@Accessors(chain = true)
public class AccountDto {
    private long id;
    private String name;
    private BigDecimal balance;
}
