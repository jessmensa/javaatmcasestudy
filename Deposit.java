

public class Deposit extends Transaction {
    private double amount; 
    private Keypad keypad; 
    private DepositSlot depositSlot; 
    private final static int CANCELED = 0; 

    // constructor 
    public Deposit(int userAccountNumber, Screen atmScreen, BankDatabase atmBankDatabase, Keypad atmKeypad, DepositSlot atmDepositSlot) {
        super(userAccountNumber, atmScreen, atmBankDatabase); 

        // initialise refrences to keypad and deposit slot 
        keypad = atmKeypad; 
        depositSlot = atmDepositSlot; 
    }

    // prompt user to enter a deposit amount 
    private double promptForDepositAmount() {
        Screen screen = getScreen(); 
        //display  the prompt 
        screen.displayMessage("\nPlease enter a deposit amount in " + "CENTS(or 0 to cancel): ");
        int input = keypad.getInput(); 

        if (input == CANCELED) {
            return CANCELED; 
        }else {
            return (double) input / 100; 
        }
    }


    // perform transaction 
    @Override 
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase(); 
        Screen screen = getScreen(); 
        amount = promptForDepositAmount(); 

        if (amount != CANCELED) {
            screen.displayMessage("\nPlease insert a deposit envelope containing ");
            screen.displayDollarAmount(amount);
            screen.displayMessageLine(".");

            // recieve deposit envelope 
            boolean envelopeRecieved = depositSlot.isEnvelopRecieved(); 

            // check whether deposit envelope was recieved 
            if (envelopeRecieved) {
                screen.displayMessageLine(
                    "\nYour envelope has been " + "recieved.\nNote: The money just deposited will not " + 
                    "be available until we verfiy the amount of any " + "enclosed cash and your checks clear"
                );
                bankDatabase.credit(getAccountNumber(), amount); 
            }
            else {
                screen.displayMessageLine("\nYou did not insert an " + "envelope, so the ATM has canceled your transaction");
            }
        }
        else {
            screen.displayMessageLine("\nCanceling transaction...");
        }
    }
    
}
