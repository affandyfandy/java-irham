public class CurrentAccount implements Account {
    private double balance;
    private double overdraftLimit;

    public CurrentAccount(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean withdraw(double amount) {
        if (balance + overdraftLimit >= amount) {
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
    public void setOverdraftLimit(double limit) {
        this.overdraftLimit = limit;
    }
}