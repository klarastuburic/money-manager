package moneymanager.dao.test;

import static junit.framework.TestCase.assertTrue;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.User;



public class TestUser {
private static Logger log = Logger.getLogger(TestUser.class);
	

	@BeforeClass
	public static void setup() {
		log.info("Creating couple of users");
		UserDAO.init();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testGetAllUsers() throws CustomException {
		List<User> allUsers = UserDAO.getAllUsers();
		assertTrue(allUsers.size() > 1);
	}

	@Test
	public void testGetUserById() throws CustomException {
		User user = UserDAO.getUserById(1);
		assertTrue(user.getUserName().equals("Marko"));
	}

	@Test(expected = CustomException.class)
	public void testGetNonExistingUserById() throws CustomException {
		User user = UserDAO.getUserById(50);
	}

	@Test
	public void testAddUser() throws CustomException {
		User user = new User(5, "ivica", "ivica@gmail.com");
		UserDAO.addUser(user);
		assertTrue(UserDAO.getUserById(5).getUserName().equals("ivica"));
		assertTrue(UserDAO.getUserById(5).getEmailAddress().equals("ivica@gmail.com"));
	}

	@Test
	public void testUpdateUser() throws CustomException {
		User user = new User(6, "test", "test@gmail.com");
		UserDAO.addUser(user);
		UserDAO.updateUser(6, "test", "new@gmail.com");
		assertTrue(UserDAO.getUserById(6).getEmailAddress().equals("new@gmail.com"));
		
	}

	@Test(expected = CustomException.class)
	public void testUpdateNonExistingUser() throws CustomException {
		UserDAO.updateUser(500, "Marica", "email@gmail.com");
	}

	@Test(expected = CustomException.class)
	public void testDeleteUser() throws CustomException {
		User user = new User(9, "test", "test@gmail.com");
		assertTrue(UserDAO.getUserById(9).getEmailAddress().equals("test@gmail.com"));
		UserDAO.deleteUser(9);
		UserDAO.getUserById(9);
	}

	@Test(expected = CustomException.class)
	public void testDeleteNonExistingUser() throws CustomException {
		UserDAO.deleteUser(10);

	}

}
