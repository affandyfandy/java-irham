public class ATMImpl implements ATM {
    private int idATM;
    private int accountId;
    private Account account;

    public ATMImpl(int idATM) {
        this.idATM = idATM;
    }

    @Override
    public boolean withdraw(int accountId, double amount) {
        if (account != null) {
            return account.withdraw(amount);
        }
        return false;
    }

    @Override
    public boolean deposit(int accountId, double amount) {
        if (account != null) {
            return account.deposit(amount);
        }
        return false;
    }

    @Override
    public double queryBalance(int accountId) {
        if (account != null) {
            return account.getBalance();
        }
        return 0.0;
    }

    @Override
    public boolean login(String username, String password) {
        // Implement logic for login
        System.out.println(username + " logged in successfully.");
        // For demonstration, always return true
        return true;
    }

    @Override
    public boolean logout(String username) {
        // Implement logic for logout
        System.out.println(username + " logged out successfully.");
        // For demonstration, always return true
        return true;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}