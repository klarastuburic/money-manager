package moneymanager.model;


import java.util.HashMap;
import java.util.Map;

import org.h2.engine.SysProperties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import moneymanager.dao.UserDAO;
import moneymanager.exception.CustomException;

public class User {

    @JsonProperty
    private int userId ;


    @JsonProperty(required = true)
    private String userName;


    @JsonProperty(required = true)
    private String emailAddress;

    @JsonProperty(required = true)
    private double amount;
    
    @JsonProperty
    private Map<Integer,Double> debts;

    public User() {}

    public User(int userId,String userName, String emailAddress) {
    	this.userId = userId;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.amount = 0;
        this.debts = new HashMap<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setDebts(Map<Integer, Double> debts) {
		this.debts = debts;
	}
        
	public Map<Integer, Double> getDebts() {
		return debts;
	}

	public String getUserName() {
        return userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
    
    public double getAmount() {
    	return amount;
    }
    
    public void setAmount(double amount) {
    	this.amount = amount;
    }
    
    public void expense(double amount) throws CustomException {
    	this.amount -= amount;
    	UserDAO.updateUser(userId, userName, emailAddress, this.amount);    	
    }
    
    public void profit(double amount) throws CustomException {
    	this.amount += amount;
    	UserDAO.updateUser(userId, userName, emailAddress, this.amount);
    }    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (!userName.equals(user.userName)) return false;
        return emailAddress.equals(user.emailAddress);

    }

	@Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + userName.hashCode();
        result = 31 * result + emailAddress.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User: " +
                "userId= " + userId +
                ", userName= " + userName  +
                ", emailAddress= " + emailAddress  +
                ", amount= " + amount;           
    }

}
