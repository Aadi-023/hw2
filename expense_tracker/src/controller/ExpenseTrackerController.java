package controller;

import view.ExpenseTrackerView;

import java.util.List;



import model.ExpenseTrackerModel;
import model.Transaction;
public class ExpenseTrackerController {
  
  private ExpenseTrackerModel model;
  private ExpenseTrackerView view;

  public ExpenseTrackerController(ExpenseTrackerModel model, ExpenseTrackerView view) {
    this.model = model;
    this.view = view;

    // Set up view event handlers
  }

  public void refresh() {

    // Get transactions from model
    List<Transaction> transactions = model.getTransactions();

    // Pass to view
    view.refreshTable(transactions);

  }

  public boolean addTransaction(double amount, String category) {
    if (!InputValidation.isValidAmount(amount)) {
      return false;
    }
    if (!InputValidation.isValidCategory(category)) {
      return false;
    }
    
    Transaction t = new Transaction(amount, category);
    model.addTransaction(t);
    view.getTableModel().addRow(new Object[]{t.getAmount(), t.getCategory(), t.getTimestamp()});
    refresh();
    return true;
  }
  
  // Other controller methods

  // Method for applying filter 
  public void applyFilter(String filterType, String value) {
    TransactionFilter filter;

    if (filterType.equalsIgnoreCase("amount")) {
        try {
            double amount = Double.parseDouble(value);
            if (!InputValidation.isValidAmount(amount)) {
                javax.swing.JOptionPane.showMessageDialog(null, "Invalid amount entered for filter.");
                return;
            }
            filter = new AmountFilter();
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Invalid amount format.");
            return;
        }

    } else if (filterType.equalsIgnoreCase("category")) {
        if (!InputValidation.isValidCategory(value)) {
            javax.swing.JOptionPane.showMessageDialog(null, "Invalid category entered for filter.");
            return;
        }
        filter = new CategoryFilter();

    } else {
        javax.swing.JOptionPane.showMessageDialog(null, "Unknown filter type.");
        return;
    }

    List<Transaction> filtered = filter.filter(model.getTransactions(), value);
    view.refreshTable(filtered);
  }


  // For test cases
  public List<Transaction> applyFilterForTest(String type, String value) {
    TransactionFilter filter;

    if (type.equalsIgnoreCase("amount")) {
        filter = new AmountFilter();
    } else if (type.equalsIgnoreCase("category")) {
        filter = new CategoryFilter();
    } else {
        return List.of();
    }

    return filter.filter(model.getTransactions(), value);
  }


}
  