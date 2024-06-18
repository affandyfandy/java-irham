public interface Account {
    boolean withdraw(double amount);
    boolean deposit(double amount);
    double getBalance();

    // Default Method
    default void printAccountDetails() {
        System.out.println("Account balance: " + getBalance());
    }

    // Static Method
    static String getAccountType() {
        return "Generic Account";
    }
}