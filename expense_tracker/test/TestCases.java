// package test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.beans.Transient;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controller.ExpenseTrackerController;
import model.ExpenseTrackerModel;
import model.Transaction;
import view.ExpenseTrackerView;

public class TestCases {
    private ExpenseTrackerModel model;
    private ExpenseTrackerView view;
    private ExpenseTrackerController controller;

  @Before
  public void setup() {
    model = new ExpenseTrackerModel();
    view = new ExpenseTrackerView();
    controller = new ExpenseTrackerController(model, view);
  }

    public double getTotalCost() {
        double totalCost = 0.0;
        List<Transaction> allTransactions = model.getTransactions(); // Using the model's getTransactions method
        for (Transaction transaction : allTransactions) {
            totalCost += transaction.getAmount();
        }
        return totalCost;
    }
    
    @Test
    public void testAddTransactionNew() {
        assertEquals(0, model.getTransactions().size());
        assertTrue(controller.addTransaction(50, "food"));
        assertEquals(1, model.getTransactions().size());
        assertEquals(50.00, getTotalCost(), 0.01);
    }
    
    @Test
    public void testInvalidInputHandling() {
        boolean invalidInput1 = controller.addTransaction(20, "expenses"); // expenses is not a category
        assertFalse(invalidInput1); 
        assertEquals(0, model.getTransactions().size()); 

        boolean invalidInput2 = controller.addTransaction(-20, "other"); // negative amount
        assertFalse(invalidInput2); 
        assertEquals(0, model.getTransactions().size());

        boolean invalidInput3 = controller.addTransaction(1010, "other"); // amount greater than 1000
        assertFalse(invalidInput3); 
        assertEquals(0, model.getTransactions().size());
    }

    @Test
    public void filterByAmount(){
        assertEquals(0, model.getTransactions().size());
        controller.addTransaction(100, "food");
        controller.addTransaction(150, "travel");
        controller.addTransaction(200, "bills");
        controller.addTransaction(100, "other");

        List<Transaction> filtered = controller.applyFilterForTest("amount", "100");
        assertEquals(2, filtered.size());
        for (Transaction t : filtered) {
            assertEquals(100.0, t.getAmount(), 0.01);
        }
    }

    @Test
    public void filterByCategory(){
        assertEquals(0, model.getTransactions().size());
        controller.addTransaction(100, "food");
        controller.addTransaction(150, "travel");
        controller.addTransaction(200, "bills");
        controller.addTransaction(300, "food");

        List<Transaction> filtered = controller.applyFilterForTest("category", "food");
        assertEquals(2, filtered.size());
        for (Transaction t : filtered) {
            assertEquals("food", t.getCategory());
        }
    } 
}
