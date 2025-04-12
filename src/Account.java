import java.util.HashMap;

abstract public class Account {
    protected String accountNumber;
    protected String accountType;
    protected Customer customerInfo;
    protected double balance;


    public Account(String accountNumber, String accountType, Customer customerInfo, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.customerInfo = customerInfo;
        this.balance = balance;
    }

    public Account(String accountNumber, String accountType, Customer customerInfo) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.customerInfo = customerInfo;
        this.balance = 0;
    }

    public Customer getCustomer() {
        return this.customerInfo;
    }


    public double getBalance() {
        return this.balance;
    }


    static {
        System.out.println("Thanks for creating an account!");
    }

    abstract public void deleteTransaction(int index);

    abstract public void getTransactionHistory();

}
