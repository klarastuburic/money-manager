package moneymanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import moneymanager.dao.impl.DebtsDAO;
import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class UserTransaction {

	@JsonProperty(required = true)
	private int fromUserId;

	@JsonProperty(required = true)
	private int toUserId;
	
	@JsonProperty(required = true)
	private double amount;

	private static Map<Integer,User> list = new HashMap<Integer,User>();
	private static Map<Integer,HashMap<Integer, Double>> currentState = new HashMap<Integer,HashMap<Integer,Double>>();
	


	public UserTransaction() {
	}

	public UserTransaction(int fromUserId, int toUserId, double amount) throws CustomException {
		this.amount = amount;
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;	    
	}

	
	public void manageTransactions() throws CustomException {
		expendBalance(toUserId);
		reduceBalance(fromUserId);
		DebtsDAO.manageNewTransaction(toUserId,fromUserId, amount);
	}

	public void reduceBalance(int id) throws CustomException { //smanjuje se dug - mora mu netko vratiti
		User user = UserDAO.getUserById(id);
		user.profit(amount);
	}
	
	public void expendBalance(int id) throws CustomException { //povecava se dug - mora nekome vratiti
		User user = UserDAO.getUserById(id);
		user.expense(amount);
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
