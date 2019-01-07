package moneymanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;

import java.math.BigDecimal;

public class UserTransaction {

	@JsonProperty(required = true)
	private int fromUserId;

	@JsonProperty(required = true)
	private int toUserId;
	
	@JsonProperty(required = true)
	private double amount;


	public UserTransaction() {
	}

	public UserTransaction(int fromUserId, int toUserId, double amount) throws CustomException {
		this.amount = amount;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		
	}
	
	public void reduceBalance(int id) throws CustomException {
		User user = UserDAO.getUserById(id);
		user.expense(amount);
	}
	
	public void expendBalance(int id) throws CustomException {
		User user = UserDAO.getUserById(id);
		user.profit(amount);
	}
	

	public double getAmount() {
		return amount;
	}

	public int getFromUserId() {
		return fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	/*
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		UserTransaction other = (UserTransaction) o;
		
		if (amount != other.amount) {
			return false;
		}
		if (fromAccountId != other.fromAccountId) {
			return false;
		}
		if(toAccountId != other.toAccountId) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 31 * result + amount.hashCode();
		result = 31 * result + fromAccountId.hashCode();
		result = 31 * result + toAccountId.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "UserTransaction{" + "currencyCode='" + currencyCode + '\'' + ", amount=" + amount + ", fromAccountId="
				+ fromAccountId + ", toAccountId=" + toAccountId + '}';
	} */

}
