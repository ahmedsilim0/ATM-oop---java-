import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

	private String fName;

	private String lName;

	private String uuid;

	private byte pinHash[];

	private ArrayList<Account> accounts;

	public User(String firstName, String lastName, String apin, Bank theBank) {
		this.fName = firstName;
		this.lName = lastName;

		// create the MD5 hash
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.pinHash = md.digest(apin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error , caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}

		// create uuid for user
		this.uuid = theBank.getNewUserUUID();

		// create emptylist of accounts
		this.accounts = new ArrayList<Account>();

		// print log message
		System.out.printf("New User Created with %s , %s with ID %s .\n", firstName, lastName, this.uuid);
	}

	public void addAccount(Account anAccount) {
		this.accounts.add(anAccount);
	}

	public String getUuid() {
		return uuid;
	}

	public boolean validatePin(String apin) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			return MessageDigest.isEqual(md.digest(apin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			System.err.println("error , caught NoSuchAlgorithmException");
			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}

	public void printAccountSummary() {
		System.out.printf("\n\n account summary", this.fName);
		for (int i = 0; i < accounts.size(); i++) {
			System.out.printf("%d) %s\n", i + 1, this.accounts.get(i).getSummaryLine());
		}

	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public int numAccoutns() {

		return accounts.size();
	}

	public void printAccountTransactionHistory(int theAcct) {
		this.accounts.get(theAcct).printTransHistory();

	}

	public double getAcctBalance(int fromAcct) {
		return this.accounts.get(fromAcct).getBalance();
	}

	public Object getAcctUUID(int toAcct) {
		return this.accounts.get(toAcct).getUuid();
	}

	public void addAccountTransaction(int fromAcct, double amount, String memo) {
		this.accounts.get(fromAcct).addTransaction(amount, memo);
	}

}
