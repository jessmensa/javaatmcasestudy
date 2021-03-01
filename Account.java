public class Account {
    private int accountNumber; 
    private int pin; 
    private double availableBalance; 
    private double totalBalance; 

    // Account constructor 
    public Account(int theAccountNumber, int thePIN, double theAvailableBalance, double theTotalBalance) {
        accountNumber = theAccountNumber; 
        pin = thePIN; 
        availableBalance = theAvailableBalance;
        totalBalance = theTotalBalance; 
    }


    // Validate user pin 
    public boolean validatePIN(int userPIN) {
        if (userPIN == pin) {
            return true;
        } else {
            return false;
        }
    }

    // returns available balance 
    public double getAvailableBalance() {
        return availableBalance; 
    }

    // returns the total balance 
    public double getTotalBalance() {
        return totalBalance; 
    }

    // credits an amount to the account 
    public void credit(double amount) {
        totalBalance += amount; 
    }

    // debits an amount from account 
    public void debit(double amount) {
        availableBalance -= amount; 
        totalBalance -= amount; 
    }

    // returns account number 
    public int getAccountNumber() {
        return accountNumber; 
    }
}