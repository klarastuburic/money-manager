package moneymanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import moneymanager.exception.CustomException;

public class CommonExpense {
	
	@JsonProperty(required = true)
	private int payerId;

	@JsonProperty(required = true)
	private String debtors;
	
	@JsonProperty(required = true)
	private double amount;
	
	public CommonExpense() {		
	}
	
	public CommonExpense(int payerId, String debtors, double amount) {
		this.payerId = payerId;
		this.debtors = debtors;
		this.amount = amount;
	}
	
	public int getPayerId() {
		return payerId;
	}
	
	public void setPayerId(int payerId) {
		this.payerId = payerId;
	}
	
	public String getDebtors() {
		return debtors;
	}
	
	public void setDebtors(String debtors) {
		this.debtors = debtors;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void divideCost() throws CustomException {
		String[] debtorsArray = debtors.split(" ");
		int n = debtorsArray.length;
		double perEach = amount / (double) (n+1);
		
		for(int i = 0; i < n; i++) {
			int debtorId = Integer.parseInt(debtorsArray[i]);
			UserTransaction transaction = new UserTransaction(payerId,debtorId,perEach);
			transaction.manageTransactions();
		}
	}
}
