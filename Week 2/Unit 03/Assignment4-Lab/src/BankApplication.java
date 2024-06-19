public class BankApplication {
    public static void main(String[] args) {
        // Create a bank account with an initial balance
        BankAccount account = new BankAccount(1000.00);

        // Create threads for concurrent deposits and withdrawals
        Thread t1 = new DepositThread(account, 200.00);
        Thread t2 = new DepositThread(account, 150.00);
        Thread t3 = new WithdrawThread(account, 100.00);
        Thread t4 = new WithdrawThread(account, 500.00);
        Thread t5 = new WithdrawThread(account, 300.00);

        // Start the threads
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
            t5.join();
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted: " + e.getMessage());
        }

        // Print final balance
        System.out.println("Final balance: " + account.getBalance());
    }
}
