import java.util.Scanner;

public class ATM {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		Bank theBank = new Bank("CIB");

		User user1 = theBank.addUser("ahmed", "samir", "1234");

		Account newAccount = new Account("checking", user1, theBank);

		user1.addAccount(newAccount);
		theBank.addNewAccount(newAccount);

		User currUser;

		while (true) {
			currUser = ATM.mainMenuPrompt(theBank, sc);
			ATM.printUserMenu(currUser, sc);
		}

	}

	private static void printUserMenu(User theUser, Scanner sc) {

		theUser.printAccountSummary();
		int choice;
		do {
			System.out.printf("Welcome %s , what would you like to do?", theUser.getfName());
			System.out.println(" 1) show accout transaction history");
			System.out.println(" 2) WithDraw");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.println();
			System.out.println("Enter choice");
			choice = sc.nextInt();

			if (choice < 1 || choice > 5) {
				System.out.println("Invalid number");
			} else if (choice == 1) {
				ATM.showTransactionHistory(theUser, sc);
			} else if (choice == 2) {
				ATM.withdrawFunds(theUser, sc);
			} else if (choice == 3) {
				ATM.depositeFunds(theUser, sc);
			} else {
				ATM.transferFunds(theUser, sc);
			}
		} while (choice != 5);
	}

	private static void transferFunds(User theUser, Scanner sc) {
		int fromAcct;
		int toAcct;
		double amount;
		double accBalance;

		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ");
			fromAcct = sc.nextInt();
			if (fromAcct < 0 || fromAcct >= theUser.numAccoutns()) {
				System.out.println("Invalid account please try again");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccoutns());
		accBalance = theUser.getAcctBalance(fromAcct);
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer to: ");
			toAcct = sc.nextInt();
			if (toAcct < 0 || toAcct >= theUser.numAccoutns()) {
				System.out.println("Invalid account please try again");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccoutns());

		do {
			System.out.printf("Enter the amount of money from ", accBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("amount must be greater than 0");
			} else if (amount > accBalance) {
				System.out.println("amount should be less than the balance of account");
			}
		} while (amount < 0 || amount > accBalance);

		theUser.addAccountTransaction(fromAcct, -1 * amount,
				String.format("Transfer to account %s", theUser.getAcctUUID(toAcct)));

		theUser.addAccountTransaction(toAcct, -1 * amount,
				String.format("Transfer to account %s", theUser.getAcctUUID(fromAcct)));
	}

	private static void depositeFunds(User theUser, Scanner sc) {
		int toAcct;
		double amount;
		double accBalance;
		String memo;

		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ");
			toAcct = sc.nextInt();
			if (toAcct < 0 || toAcct >= theUser.numAccoutns()) {
				System.out.println("Invalid account please try again");
			}
		} while (toAcct < 0 || toAcct >= theUser.numAccoutns());

		accBalance = theUser.getAcctBalance(toAcct);
		do {
			System.out.printf("Enter the amount of money from ", accBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("amount must be greater than 0");
			} else if (amount > accBalance) {
				System.out.println("amount should be less than the balance of account");
			}
		} while (amount < 0 || amount > accBalance);

		sc.nextLine();

		System.out.println("Enter a memo");
		memo = sc.nextLine();
		theUser.addAccountTransaction(toAcct, amount, memo);

	}

	private static void withdrawFunds(User theUser, Scanner sc) {
		int fromAcct;
		double amount;
		double accBalance;
		String memo;

		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "to transfer from: ");
			fromAcct = sc.nextInt();
			if (fromAcct < 0 || fromAcct >= theUser.numAccoutns()) {
				System.out.println("Invalid account please try again");
			}
		} while (fromAcct < 0 || fromAcct >= theUser.numAccoutns());

		accBalance = theUser.getAcctBalance(fromAcct);
		do {
			System.out.printf("Enter the amount of money from ", accBalance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("amount must be greater than 0");
			} else if (amount > accBalance) {
				System.out.println("amount should be less than the balance of account");
			}
		} while (amount < 0 || amount > accBalance);

		sc.nextLine();

		System.out.println("Enter a memo");
		memo = sc.nextLine();
		theUser.addAccountTransaction(fromAcct, -1 * amount, memo);
	}

	private static void showTransactionHistory(User theUser, Scanner sc) {
		int theAcct;
		do {
			System.out.printf("Enter the number (1-%d) of the account\n" + "whoose transaction yo want to see:",
					theUser.numAccoutns());
			theAcct = sc.nextInt();
			if (theAcct < 0 || theAcct >= theUser.numAccoutns()) {
				System.out.println("Invalid account , pls try again.");
			}
			theUser.printAccountTransactionHistory(theAcct);
		} while (theAcct < 0 || theAcct >= theUser.numAccoutns());

		theUser.printAccountTransactionHistory(theAcct);
	}

	private static User mainMenuPrompt(Bank theBank, Scanner sc) {

		String userId;
		String pin;
		User userAuth;
		do {
			System.out.printf("\n\nWelcome to %s \n\n", theBank.getName());
			System.out.print("Enter user ID ");
			userId = sc.nextLine();
			System.out.print("Enter pin");
			pin = sc.nextLine();
			userAuth = theBank.userLogin(userId, pin);
			if (userAuth == null) {
				System.out.println("incorrect User ID/pin");
			}
		} while (userAuth == null);
		return userAuth;
	}
}
