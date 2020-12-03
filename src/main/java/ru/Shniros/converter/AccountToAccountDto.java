package ru.Shniros.converter;

import org.springframework.core.convert.converter.Converter;
import ru.Shniros.domain.Account;
import ru.Shniros.service.dto.AccountDto;

public class AccountToAccountDto implements Converter<Account, AccountDto> {
    @Override
    public AccountDto convert(Account account) {
        return new AccountDto().setId(account.getId())
                .setName(account.getName())
                .setBalance(account.getBalance());
    }
}
