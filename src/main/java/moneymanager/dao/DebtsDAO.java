package moneymanager.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import moneymanager.exception.CustomException;
import moneymanager.model.User;

/**
 * Representing data access object for debts between all users
 * @author klara
 *
 */
public class DebtsDAO {
	private static Logger log = Logger.getLogger(DebtsDAO.class);
	private static Map<Integer, HashMap<Integer, Double>> debts;

	static {
		init();
	}

	public static void init() {
		debts = new HashMap<Integer, HashMap<Integer, Double>>();
	}

	/**
	 * Managing new debt according to potential previous debts
	 * @param fromUserId user that owes money
	 * @param toUserId user that lent money
	 * @param amount amount of the money fronUserId borrowed to toUserId
	 * @throws CustomException
	 */
	public static void manageNewDebt(int fromUserId, int toUserId, double amount) throws CustomException {
		
		//first debt for user - fromUserId
		if (!exists(fromUserId)) {
			HashMap<Integer, Double> map = new HashMap<Integer, Double>();
			debts.put(fromUserId, map);
			
			//if other user has some debts from before, check if it has debts to the first one and manage it if it has, otherwise add debt to the first one
			if (exists(toUserId)) {
				if (debts.get(toUserId).containsKey(fromUserId)) {
					reduceDebt(fromUserId, toUserId, amount);
				}
			} else {
				expandDebt(fromUserId, toUserId, amount);
			}
		
		//fromUserId has some debts from before, then check if other user has debts to the first one
		} else {
			if (exists(toUserId) && debts.get(toUserId).get(fromUserId) != 0.0) {
				if (debts.get(toUserId).containsKey(fromUserId)) {
					reduceDebt(fromUserId, toUserId, amount);
				}
			} else {
				expandDebt(fromUserId, toUserId, amount);
			}
		}
	}

	public static boolean exists(int fromUserId) {
		return debts.containsKey(fromUserId);
	}

	public static void expandDebt(int fromUserId, int toUserId, double amount) throws CustomException {
		if (!debts.get(fromUserId).containsKey(toUserId)) {
			debts.get(fromUserId).put(toUserId, amount);
		} else {
			double newAmount = amount + debts.get(fromUserId).get(toUserId);
			debts.get(fromUserId).put(toUserId, newAmount);
		}
	}

	public static void reduceDebt(int fromUserId, int toUserId, double amount) {
		if (debts.get(toUserId).get(fromUserId) > amount) {
			double newAmount = debts.get(toUserId).get(fromUserId) - amount;
			debts.get(toUserId).put(fromUserId, newAmount);
			debts.get(fromUserId).put(toUserId, 0.0);
		} else {
			double newAmount = amount - debts.get(toUserId).get(fromUserId);
			debts.get(fromUserId).put(toUserId, newAmount);
			debts.get(toUserId).put(fromUserId, 0.0);
		}
	}

	public static Map<Integer, HashMap<Integer, Double>> getAllDebts() {
		return debts;
	}
}
