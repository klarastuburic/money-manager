package moneymanager;

import moneymanager.dao.impl.DebtsDAO;
import moneymanager.dao.impl.UserDAO;
import moneymanager.model.UserTransaction;
import moneymanager.service.CommonExpenseService;
import moneymanager.service.CommonProfitService;
import moneymanager.service.ServiceExceptionMapper;
import moneymanager.service.TransactionService;
import moneymanager.service.UserService;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * Main Class (Starting point) 
 */
public class Application {

	private static Logger log = Logger.getLogger(Application.class);

	public static void main(String[] args) throws Exception {

		log.info("*****INITIALIZATION STARTED******");
		UserDAO.init();
		DebtsDAO.init();
		log.info("*****INITIALIZATION COMPLETED******");		
		startService(); //starting jetty service
		log.info("*****SERVICE STARTED*****");
	}

	private static void startService() throws Exception {
		Server server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		server.setHandler(context);
		ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
		servletHolder.setInitParameter("jersey.config.server.provider.classnames",
				UserService.class.getCanonicalName() +  "," + TransactionService.class.getCanonicalName() + ","
						+ CommonExpenseService.class.getCanonicalName() + "," +CommonProfitService.class.getCanonicalName() + ","+ ServiceExceptionMapper.class.getCanonicalName());
		try {
			server.start();
			server.join();
		} finally {
			server.destroy();
		}
	}

}
