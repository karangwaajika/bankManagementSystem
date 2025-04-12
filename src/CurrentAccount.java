import database.TransactionStore;

import java.time.LocalDateTime;
import java.util.HashMap;

public class CurrentAccount extends Account implements BankingOperation {

    LocalDateTime date = LocalDateTime.now();
    private final String TODAY_DATE = date.toLocalDate().toString(); // format 2025-04-12
    int transactionId = 1;
    private final double OVERDRAFT_LIMIT = 150000.00;

    TransactionStore linkedList = new TransactionStore();

    // create an account without making a deposit
    public CurrentAccount(String accountNumber, String accountType, Customer customer) {
        super(accountNumber, accountType, customer);
    }

    // create an account and make a deposit
    public CurrentAccount(String accountNumber, String accountType, Customer customer, double amount) {
        super(accountNumber, accountType, customer, amount);

        /* Record transaction history.
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
        super.balance += amount; // update the account balance

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
            System.out.println("No sufficient amount to withdraw, please use the overdraft service instead!");
        } else {
            super.balance = newBalance; // update the account balance

            recordTransactionHistory(amount, "withdraw", date);
            System.out.println("Successfully withdrawn from the account!!! ");
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

    boolean isOverdraftAllow(double amount) {
        // handle overdraft amount in case the balance is not zero or minus
        double balance = super.getBalance();
        double overdraftAmount;
        if (balance <= 0) {
            overdraftAmount = amount;
        } else {
            overdraftAmount = amount - balance;
        }

        return overdraftAmount < this.OVERDRAFT_LIMIT;

    }

    @Override
    public void overdraft(double amount) {
        if (isOverdraftAllow(amount)) {
            // first make withdraw if the balance is not zero or minus
            if (super.getBalance() >= 0) {
                this.withdraw(this.getBalance(), this.TODAY_DATE);
                double overDraftAmount = amount - super.getBalance();

                super.balance -= overDraftAmount; // update the balance

                recordTransactionHistory(overDraftAmount, "overdraft", this.TODAY_DATE);
                System.out.println("Successfully made overdraft!!!");

            } else {
                super.balance -= amount; // update the balance

                recordTransactionHistory(amount, "overdraft", this.TODAY_DATE);
                System.out.println("Successfully made overdraft!!!");

            }
        } else {
            System.out.println("you are not allow to make overdraft!!!");
        }
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

