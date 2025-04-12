import database.TransactionStore;

public class Main {
    public static void main(String[] args) {
        // create customer
        Customer customer1 = new Customer("ajika", 'M', 23);
        Customer customer2 = new Customer("joel", 'M', 23);
        // create an account and associate it with the customer
        Account saving = new SavingAccount("001", "Saving", customer1, 200);
        System.out.printf("Customer: %s\n", saving.getCustomer().name);
        ((BankingOperation) saving).deposit(120);
        ((BankingOperation) saving).deposit(10000);
        ((BankingOperation) saving).deposit(300);
        ((BankingOperation) saving).overdraft(300);
        saving.deleteTransaction(2);


        saving.getTransactionHistory();
        System.out.printf("Balance: %f\n", saving.getBalance());

        System.out.println("=====================================================");

        Account saving1 = new CurrentAccount("003", "Saving", customer2);
        System.out.printf("Customer: %s\n", saving1.getCustomer().name);

        ((BankingOperation) saving1).deposit(120);
        ((BankingOperation) saving1).deposit(50000);
        ((BankingOperation) saving1).withdraw(100);
        ((BankingOperation) saving1).overdraft(400);

        saving1.getTransactionHistory();
        System.out.printf("Balance: %f", saving1.getBalance());


    }
}