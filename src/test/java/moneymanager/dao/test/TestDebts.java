package moneymanager.dao.test;

import static junit.framework.TestCase.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import moneymanager.dao.impl.DebtsDAO;
import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.User;
import moneymanager.model.UserTransaction;

public class TestDebts {
	
	private static Logger log = Logger.getLogger(TestUser.class);
	
	@BeforeClass
	public static void setup() {
		log.info("Creating empty debts map");
		UserDAO.init();
	}

	@After
	public void tearDown() {

	}

	@Test
	public void testGetAllDebts() throws CustomException {
		UserTransaction transaction = new UserTransaction(1,2,4);
		transaction.manageTransactions();
		Map<Integer, HashMap<Integer, Double>> debts = DebtsDAO.getAllDebts();
		assertTrue(debts.size() == 1);
	}
	
	public void testCurrentDebts() throws CustomException {
		UserTransaction transaction = new UserTransaction(1, 2, 400);
		Map<Integer, HashMap<Integer, Double>> debts = DebtsDAO.getAllDebts();
		assertTrue(debts.get(2).get(1) == 400);
	}
	
	public void testCurrentDebts2() throws CustomException {
		UserTransaction transaction = new UserTransaction(1, 2, 400);
		transaction.manageTransactions();
		UserTransaction transaction2 = new UserTransaction(1, 2, 50);
		transaction2.manageTransactions();
		Map<Integer, HashMap<Integer, Double>> debts = DebtsDAO.getAllDebts();
		assertTrue(debts.get(2).get(1) == 450);
	}
	
	public void testCurrentDebts3() throws CustomException {
		UserTransaction transaction = new UserTransaction(2, 1, 400);
		transaction.manageTransactions();
		Map<Integer, HashMap<Integer, Double>> debts = DebtsDAO.getAllDebts();
		assertTrue(debts.get(2).get(1) == 50);
		assertTrue(debts.get(1).get(2) == 0);
	}
	
}
