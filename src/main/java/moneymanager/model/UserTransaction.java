package moneymanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import moneymanager.dao.DebtsDAO;
import moneymanager.dao.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.service.UserService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class UserTransaction {

	/**
	 * the user that borrowed money to another user
	 */
	@JsonProperty(required = true)
	private int fromUserId;

	/**
	 * the user who received money
	 */
	@JsonProperty(required = true)
	private int toUserId;
	
	@JsonProperty(required = true)
	private double amount;
	
	private static Logger log = Logger.getLogger(UserTransaction.class);

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
		expendDebt(toUserId);
		reduceDebt(fromUserId);
		DebtsDAO.manageNewDebt(toUserId,fromUserId, amount);
	}

	public void reduceDebt(int id) throws CustomException { 
		User user = UserDAO.getUserById(id);
		user.profit(amount);
		log.debug("After transaction:" + user.toString());
	}
	
	public void expendDebt(int id) throws CustomException { 
		User user = UserDAO.getUserById(id);
		user.expense(amount);
		log.debug("After transaction:" + user.toString());
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

}
