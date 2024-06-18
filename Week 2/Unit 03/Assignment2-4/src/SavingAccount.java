public class SavingAccount implements Account {
    private double balance;

    @Override
    public boolean withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public boolean deposit(double amount) {
        balance += amount;
        return true;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    // Unique Method
    public void addInterest(double interestRate) {
        balance += balance * interestRate;
    }
}