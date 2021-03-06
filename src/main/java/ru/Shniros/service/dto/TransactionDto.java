package ru.Shniros.service.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class TransactionDto {
    private long id;
    private Date date;
    private String comment;
    private long fromAccountId;
    private long toAccountId;
    private BigDecimal sum;
    private int categoryId;
}
