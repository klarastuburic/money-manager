package moneymanager;

import moneymanager.dao.DebtsDAO;
import moneymanager.dao.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.CommonExpense;
import moneymanager.model.CommonProfit;
import moneymanager.model.UserTransaction;
import moneymanager.service.CommonExpenseService;
import moneymanager.service.CommonProfitService;
import moneymanager.service.ServiceExceptionMapper;
import moneymanager.service.TransactionService;
import moneymanager.service.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.h2.engine.SysProperties;

public class Application {

	private static Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) throws Exception {

		log.info("*****INITIALIZATION STARTED******");
		UserDAO.init();
		DebtsDAO.init();
		log.info("*****INITIALIZATION COMPLETED******");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Choose mode: C for console client or W for web service!");
		String mode = br.readLine();

		if (mode.equals("C")) {
			log.info("Console mode started");

			while (true) {

				System.out.println("Enter action: T for transaction, P for profit, E for Expanse ");
				String action = br.readLine();

				if (action.equals("T")) {

					System.out.println("Enter lender id ");
					int id1 = Integer.parseInt(br.readLine());

					System.out.println("Enter loaner id ");
					int id2 = Integer.parseInt(br.readLine());

					System.out.println("Enter amount ");
					double amount = Double.parseDouble(br.readLine());

					UserTransaction transaction = new UserTransaction(id1, id2, amount);
					transaction.manageTransactions();
					printMap(DebtsDAO.getAllDebts());

				} else if (action.equals("P")) {

					System.out.println("Enter id of the profit receiver");
					int id1 = Integer.parseInt(br.readLine());

					System.out.println("Enter others ids separeted by spaces, example: 1 2");
					String others = br.readLine();

					System.out.print("Enter amount");
					double amount = Double.parseDouble(br.readLine());

					CommonProfit transaction = new CommonProfit(id1, others, amount);
					transaction.divideProfit();
					printMap(DebtsDAO.getAllDebts());

				} else if (action.equals("E")) {

					System.out.println("Enter id of the user who payed: ");
					int payerId = Integer.parseInt(br.readLine());

					System.out.println("Enter others ids separated by spaces, example: 1 2");
					String others = br.readLine();

					System.out.println("Enter amount");
					Double amount = Double.parseDouble(br.readLine());

					CommonExpense transaction = new CommonExpense(payerId, others, amount);
					transaction.divideCost();
					printMap(DebtsDAO.getAllDebts());

				} else {
					System.out.println("Invalid action");
				}
			}

		} else if (mode.equals("W")) {
			log.info("Web service mode started");
			startService(); // starting jetty service
		} else {
			System.out.println("Invalid action");
		}

	}

	private static void startService() throws Exception {
		Server server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",
				UserService.class.getCanonicalName() + "," + TransactionService.class.getCanonicalName() + ","
						+ CommonExpenseService.class.getCanonicalName() + ","
						+ CommonProfitService.class.getCanonicalName() + ","
						+ ServiceExceptionMapper.class.getCanonicalName());
		try {
			server.start();
			server.join();
		} finally {
			server.destroy();
		}
	}

	private static void printMap(Map<Integer,HashMap<Integer,Double>> map) throws CustomException {
		for(Map.Entry<Integer, HashMap<Integer,Double>> t :map.entrySet()){
			Integer userId = t.getKey();
			System.out.println(UserDAO.getUserById(userId).getUserName() + 
					", id = " +userId + " owes to: ");
			for (Map.Entry<Integer,Double> e : t.getValue().entrySet())
				System.out.println("	" + UserDAO.getUserById(e.getKey()).getUserName() + 
						", id = "+ e.getKey()+ " -- " +e.getValue() + "kn");
			}
	}

}
