public class ATM {
    private boolean userAuthenticated; 
    private int currentAccountNumber; 
    private Screen screen; 
    private Keypad keypad; 
    private BankDatabase bankDatabase; 
    private DepositSlot depositSlot; 
    private CashDispenser cashDispenser; 


    // constants rep main menu options 
    private static final int BALANCE_INQUIRY = 1; 
    private static final int WITHDRAWAL = 2; 
    private static final int DEPOSIT = 3; 
    private static final int EXIT = 4; 


    // constructor 
    public ATM() {
        userAuthenticated = false; 
        currentAccountNumber = 0; 
        screen = new Screen(); 
        keypad = new Keypad(); 
        cashDispenser = new CashDispenser(); 
        depositSlot = new DepositSlot(); 
        bankDatabase = new BankDatabase(); 
    }

    // check if user in account number dey the database inside 
    private void authenticateUser() {
        // display message make he say make he input in account number 
        screen.displayMessage("\nPlease enter your account number: ");
        // claim the account number he input nu 
        int accountNumber = keypad.getInput(); 
        // display message say make he enter in pin number 
        screen.displayMessage("\nPlease enter your PIN: ");
        // claim the pin number he input nu 
        int pin = keypad.getInput(); 

        // since userAuthenticated be false, 
        // enter BankDatabase class inside through instance of the class nu 
        // then use method authenticateUser plus account number then pin wey user provide for here
        // then set am to userAuthenticated boolean variable 
        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin); 

        // check whether ebe true say account number dey database inside 
        // if userAuthenticated 
        if (userAuthenticated) {
            // currentAccount number for database for be equal to account number 
            // wey user provide for here 
            currentAccountNumber = accountNumber; 
            // if eno be so display message say 
        } else {
            screen.displayMessageLine("Invalid account number or PIN. Please try again");

        }
    }

    // display main menu show user 
    private int displayMainMenu() {
        // tap into Screen class using instance then use display messagge line method nu 
        screen.displayMessageLine("\nMain Menu");
        screen.displayMessageLine("1 - View my balance");
        screen.displayMessageLine("2 - Withdraw cash");
        screen.displayMessageLine("3 - Deposit funds");
        screen.displayMessageLine("4 - Exit\n");
        screen.displayMessageLine("Enter a choice: ");
        // take input wey user do 
        return keypad.getInput(); 
    }

    // method wey dey return menu selections wey user choose during transaction 
    // based on the type of transaction 
    private Transaction createTransaction(int type) {
        // transaction be null at this moment dn store for variable inside 
        Transaction temp = null; 

        // switch statement take display results based on user in choice 
        switch (type) {
            // user choose balance inquiry ah 
            case BALANCE_INQUIRY: 
            // balance inquiry go be currentAccountNumber, screen, then bankDatabase 
            temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase); 
            break; 
            // user choose withdrawal ah 
            case WITHDRAWAL:
            // ego show am 
            temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser); 
            // user choose deposit ah 
            break; 
            case DEPOSIT: 
            // show am 
            temp = new Deposit(currentAccountNumber, screen, bankDatabase, keypad, depositSlot); 
            break; 
        }
        // return(show) the one user choose. 
        return temp; 

    }


    // now method wey wey go take perform transactions 
    private void performTransactions() {
        // create instance of transaction class then set am to null 
        Transaction currentTransaction = null; 

        // create boolean variable wey say true or false if user exit or shun transaction 
        boolean userExited = false; 

        // so far as user no exited transaction 
        while (!userExited) {
            // mainMenuSelection variable be equal to displayMenuMethod 
            // sekoff?? take show menu messages to user 
            int mainMenuSelection = displayMainMenu(); 

            // on user in selection, choose what go happen 
            switch (mainMenuSelection) {
                case BALANCE_INQUIRY: 
                case WITHDRAWAL: 
                case DEPOSIT: 
                // based on mainMenuSelection for createTransaction method 
                // store am for currentTransaction 
                currentTransaction = createTransaction(mainMenuSelection); 
                // tapinto execute method for Transaction class inside 
                // based on balance inquiry or withdrawl or deposit 
                // execute one of methods 
                currentTransaction.execute();
                break; 
                // user choose exit ah 
                // display message 
                // userExited boolean be set to false 
                case EXIT:
                screen.displayMessageLine("\nExiting the system...");
                userExited = true; 
                break; 
                // none of the above, user choose ah 
                // display message 
                default: 
                screen.displayMessageLine("\nYou did not enter a valid selection. Try again");
                break; 
            }
        }
    }



    // start ATM 
    public void run() {
        while (true) {
            while (!userAuthenticated) {
                screen.displayMessageLine("\nWelcome!"); 
                authenticateUser(); 
            }
            performTransactions();
            userAuthenticated = false; 
            currentAccountNumber = 0; 
            screen.displayMessageLine("\nThank You, Goodbye");
        }
    }
    
}
