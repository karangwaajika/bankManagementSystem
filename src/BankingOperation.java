public interface BankingOperation {
    void deposit(double amount);

    void deposit(double amount,String date);

    void overdraft(double amount);

    void withdraw(double amount);

    void withdraw(double amount, String date);

}
