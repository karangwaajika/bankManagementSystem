import java.time.LocalDateTime;
import java.util.HashMap;

import database.TransactionStore;

public class SavingAccount extends Account implements BankingOperation {

    LocalDateTime date = LocalDateTime.now();
    private final String TODAY_DATE = date.toLocalDate().toString();
    int transactionId = 1;
    private final double MINIMUNM_AMOUNT = 500.00;
    private final double INTEREST_RATE = 0.05; // 0.5%

    TransactionStore linkedList = new TransactionStore();

    public SavingAccount(String accountNumber, String accountType, Customer customer, double amount) {
        super(accountNumber, accountType, customer, amount);

        /* Record transaction history since saving account needs a minimum amount in order to create an account.
           to keep track of transaction history, a hashmap is used to have some additional information.
           ex: {transId: 001, amount: 2000, operationType: deposit, date: 2025-01-12}
         */
        HashMap<String, String> transactionInfo = new HashMap<>();
        transactionInfo.put("transId", Integer.toString(this.transactionId));
        transactionInfo.put("amount", Double.toString(amount));
        transactionInfo.put("operationType", "deposit");
        transactionInfo.put("date", this.TODAY_DATE);

        this.linkedList.insertFirst(transactionInfo);
        this.transactionId++;

    }


    @Override
    public void deposit(double amount, String date) {
        double amountWithInterest = amount * INTEREST_RATE + amount;
        super.balance += amountWithInterest; // update the account balance

        // Record transaction history
        recordTransactionHistory(amount, "deposit", date);
        System.out.println("Amount is deposited successfully !!!");
    }

    @Override
    public void deposit(double amount) {
        this.deposit(amount, this.TODAY_DATE);
    }


    @Override
    public void withdraw(double amount, String date) {
        double oldBalance = super.getBalance();
        double newBalance = oldBalance - amount;

        if (super.getBalance() < amount) {
            System.out.println("No sufficient amount to withdraw");
        } else if (newBalance < this.MINIMUNM_AMOUNT) {
            System.out.printf("Your amount balance can't be less than %f rwf\n", this.MINIMUNM_AMOUNT);
        } else {
            super.balance = newBalance; // update the account balance

            recordTransactionHistory(amount, "withdraw", date);
            System.out.println("Successfully withdrawn from the account !!!");
        }

    }

    @Override
    public void withdraw(double amount) {
        this.withdraw(amount, this.TODAY_DATE);
    }

    @Override
    public void deleteTransaction(int index) {
        if (this.linkedList.deleteTransaction(index)) {
            System.out.println("Transaction is deleted successfully");
        } else {
            System.out.println("You no transaction history");
        }

    }

    @Override
    public void overdraft(double amount) {
        System.out.println("Sorry, you can't make overdraft in saving account");
    }


    @Override
    public void getTransactionHistory() {
        this.linkedList.displayTransactions();
    }

    // record transaction history
    void recordTransactionHistory(Double amount, String operationType, String date) {

        HashMap<String, String> transactionInfo = new HashMap<>();
        transactionInfo.put("transId", Integer.toString(this.transactionId));
        transactionInfo.put("amount", Double.toString(amount));
        transactionInfo.put("operationType", operationType);
        transactionInfo.put("date", date);

        this.linkedList.insertFirst(transactionInfo);

        this.transactionId++;
    }

}
