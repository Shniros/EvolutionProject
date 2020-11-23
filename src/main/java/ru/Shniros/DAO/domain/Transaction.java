package ru.Shniros.DAO.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
@Data
@Accessors(chain = true)
public class Transaction {
    private long id;
    private Date date;
    private String comment;
    private long fromAccountId;
    private long toAccountId;
    private BigDecimal sum;
}