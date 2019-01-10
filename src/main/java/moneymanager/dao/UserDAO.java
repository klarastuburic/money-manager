package moneymanager.dao;

import moneymanager.exception.CustomException;
import moneymanager.model.User;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representing data access object for users
 * @author klara
 *
 */
public class UserDAO {

	private static Logger log = Logger.getLogger(UserDAO.class);
	private static final Map<Integer, User> users = new HashMap<Integer, User>();
	

	static {
		init();
	}

	public static void init() {
		User user1 = new User(1, "Marko", "marko@gmail.com");
		User user2 = new User(2, "Ivana", "ivanaivanic@gmail.com");
		User user3 = new User(3, "Maja", "maja-majic@gmail.com");

		users.put(user1.getUserId(), user1);
		users.put(user2.getUserId(), user2);
		users.put(user3.getUserId(), user3);
	}

	public static List<User> getAllUsers() {
		Collection<User> c = users.values();
		List<User> list = new ArrayList<User>();
		list.addAll(c);
		return list;
	}

	public static User getUserById(int userId) throws CustomException {
		if (!users.containsKey(userId)) {
			log.fatal("CustomException occur: There is no user with that userId");
			throw new CustomException("There is no user with that userId");
		} else {
			return users.get(userId);
		}
	}

	public static User addUser(User user) throws CustomException {
		if (users.containsKey(user.getUserId())) {
			log.fatal("CustomException occur: UserId already exists");
			throw new CustomException("UserId already exists");
		}
		users.put(user.getUserId(), user);
		return user;

	}

	public static User updateUser(int userId, String name, String email, double amount) throws CustomException {
		if (!users.containsKey(userId)) {
			log.fatal("CustomException occur: There is no user with that userId");
			throw new CustomException("There is no user with that ID");
		} else {
			User user = new User(userId, name, email);
			user.setAmount(amount);
			users.put(userId, user);
			return user;
		}
	}

	public static User updateUser(int userId, String name, String email) throws CustomException {
		if (!users.containsKey(userId)) {
			log.fatal("CustomException occur: There is no user with that userId");
			throw new CustomException("There is no user with that id");
		} else {
			User user = new User(userId, name, email);
			users.put(userId, user);
			return user;
		}
	}

	public static void deleteUser(int userId) throws CustomException {
		if (!users.containsKey(userId)) {
			log.fatal("CustomException occur: There is no user with that userId");
			throw new CustomException("There is no user with that ID");
		} else {
			users.remove(userId);
		}
	}
}
