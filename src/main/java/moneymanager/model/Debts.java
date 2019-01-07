package moneymanager.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;

public class Debts {

	@JsonProperty(required = true)
	private int userId;

	@JsonProperty(required = true)
	private Map<Integer, Double> debts;

	public Debts(int userId) {
		this.userId = userId;
		debts = new HashMap<Integer, Double>();
	}

	public int getUserId() {
		return userId;
	}

	public Map<Integer, Double> getDebts() {
		return debts;
	}
	
	public void setDebts(int toUserId, double amount) {
		debts.put(toUserId, amount);
	}
	
	
}
