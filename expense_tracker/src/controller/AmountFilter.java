package controller;

import java.util.List;
import java.util.stream.Collectors;
import model.Transaction;

public class AmountFilter implements TransactionFilter {

    @Override
    public List<Transaction> filter(List<Transaction> transactions, String value) {
        try {
            double amount = Double.parseDouble(value);
            return transactions.stream()
                    .filter(t -> t.getAmount() == amount)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            return List.of(); // return empty list on invalid input
        }
    }
}
