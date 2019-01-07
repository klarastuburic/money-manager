package moneymanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import moneymanager.exception.CustomException;

public class CommonProfit {
	
	@JsonProperty(required = true)
	private int debtorId; //reciever of the profit

	@JsonProperty(required = true)
	private String others;
	
	@JsonProperty(required = true)
	private double amount;
	
	public CommonProfit() {		
	}
	
	public CommonProfit(int debtorId, String others, double amount) {
		this.debtorId = debtorId;
		this.others = others;
		this.amount = amount;
	}
	
	public void divideProfit() throws CustomException {
		String[] othersArray = others.split(" ");
		int n = othersArray.length;
		double perEach = amount / (double) (n+1);
		
		for(int i = 0; i < n; i++) {
			int otherId = Integer.parseInt(othersArray[i]);
			UserTransaction transaction = new UserTransaction(otherId,debtorId,perEach);
			transaction.manageTransactions();
		}
	}
}
