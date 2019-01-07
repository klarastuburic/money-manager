package moneymanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import moneymanager.exception.CustomException;

public class CommonProfit {
	
	@JsonProperty(required = true)
	private int fromUserId; //reciever of the profit

	@JsonProperty(required = true)
	private String others;
	
	@JsonProperty(required = true)
	private double amount;
	
	public CommonProfit() {		
	}
	
	public CommonProfit(int fromUserId, String others, double amount) {
		this.fromUserId = fromUserId;
		this.others = others;
		this.amount = amount;
	}
	
	public void divideProfit() throws CustomException {
		String[] othersArray = others.split(" ");
		int n = othersArray.length;
		double perEach = amount / (double) (n+1);
		
		for(int i = 0; i < n; i++) {
			int toUserId = Integer.parseInt(othersArray[i]);
			UserTransaction transaction = new UserTransaction(fromUserId,toUserId,perEach);
			transaction.reduceBalance(fromUserId);
			transaction.expendBalance(toUserId);
		}
	}
}
