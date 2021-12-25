import java.util.ArrayList;

public class Account {

	private String name;

	private String uuid;

	private User holder;

	private double balance;

	private ArrayList<Transaction> transactions;

	public Account(String name, User holder, Bank theBank) {
		this.name = name;
		this.holder = holder;

		// get new uuid account
		this.uuid = theBank.getNewAccountUUID();

		// add holder and bank to list

	}

	public User getHolder() {
		return holder;
	}

	public void setHolder(User holder) {
		this.holder = holder;
	}

	public String getSummaryLine() {
		double balance = this.getBalance();
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getBalance() {
		double balance = 0;
		for (Transaction t : transactions) {
			balance += t.getAmount();
		}
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void printTransHistory() {
		System.out.printf("\n Transaction history for account %s\n", this.uuid);
		for (int i = this.transactions.size() - 1; i >= 0; --i) {
			System.out.printf(this.transactions.get(i).getSummaryLine());
		}
		System.out.println();

	}

	public void addTransaction(double amount, String memo) {
		Transaction newTransaction = new Transaction(amount, memo, this);
		this.transactions.add(newTransaction);

	}
}
