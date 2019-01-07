package moneymanager.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import moneymanager.exception.CustomException;
import moneymanager.model.Debts;
import moneymanager.model.User;

public class DebtsDAO {
	private static Map<Integer, HashMap<Integer, Double>> debts;

	static {
		init();

	}

	public static void init() {
		debts = new HashMap<Integer, HashMap<Integer, Double>>();
	}

	public static void manageNewTransaction(int fromUserId, int toUserId, double amount) throws CustomException {
		if (!exists(fromUserId)) {
			HashMap<Integer, Double> map = new HashMap<Integer, Double>();
			debts.put(fromUserId, map);
			if (exists(toUserId)) {
				if (debts.get(toUserId).containsKey(fromUserId)) {
					reduceDebt(fromUserId, toUserId, amount);
				}
			} else {
				expandDebt(fromUserId, toUserId, amount);
			}
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
		System.out.println(debts.get(fromUserId));
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
