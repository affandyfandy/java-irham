public class App {
    public static void main(String[] args) throws Exception {
        ATM atm = new ATMImpl(1);
        Account savingAccount = new SavingAccount();
        Account currentAccount = new CurrentAccount(500);

        // Simulate login
        atm.login("user1", "password");

        // Set account to saving account and perform operations
        ((ATMImpl) atm).setAccount(savingAccount);
        atm.deposit(1, 1000);
        atm.withdraw(1, 200);
        atm.queryBalance(1);

        // Use default method
        savingAccount.printAccountDetails();

        // Use static method
        System.out.println(Account.getAccountType());

        // Set account to current account and perform operations
        ((ATMImpl) atm).setAccount(currentAccount);
        atm.deposit(2, 1500);
        atm.withdraw(2, 1800);
        atm.queryBalance(2);

        // Use default method
        currentAccount.printAccountDetails();

        // Simulate logout
        atm.logout("user1");
    }
}
