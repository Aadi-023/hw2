package controller;

import java.util.List;
import java.util.stream.Collectors;
import model.Transaction;

public class CategoryFilter implements TransactionFilter {

    @Override
    public List<Transaction> filter(List<Transaction> transactions, String value) {
        return transactions.stream()
                .filter(t -> t.getCategory().equalsIgnoreCase(value))
                .collect(Collectors.toList());
    }
}
