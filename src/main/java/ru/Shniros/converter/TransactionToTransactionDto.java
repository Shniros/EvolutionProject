package ru.Shniros.converter;

import org.springframework.core.convert.converter.Converter;
import ru.Shniros.domain.Transaction;
import ru.Shniros.service.dto.TransactionDto;

public class TransactionToTransactionDto implements Converter<Transaction, TransactionDto> {
    @Override
    public TransactionDto convert(Transaction transaction) {
        return new TransactionDto().setId(transaction.getId())
                .setComment(transaction.getComment())
                .setCategoryId(transaction.getCategoryId())
                .setDate(transaction.getDate())
                .setFromAccountId(transaction.getFromAccountId())
                .setToAccountId(transaction.getToAccountId())
                .setSum(transaction.getSum());
    }
}
