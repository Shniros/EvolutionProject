package ru.Shniros.mappers;

import org.mapstruct.Mapper;
import ru.Shniros.domain.Account;
import ru.Shniros.web.dto.AccountDto;

@Mapper(componentModel = "cdi")
public interface AccountMapper {
    Account toAccount(AccountDto accountDto);

    AccountDto toAccountDto(Account account);
}
