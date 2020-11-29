package ru.Shniros.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TransactionCategory {
    private int id;
    private String name;
}
