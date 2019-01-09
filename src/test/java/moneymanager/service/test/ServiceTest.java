package moneymanager.service.test;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.fasterxml.jackson.databind.ObjectMapper;

import moneymanager.dao.DebtsDAO;
import moneymanager.dao.UserDAO;
import moneymanager.service.CommonExpenseService;
import moneymanager.service.CommonProfitService;
import moneymanager.service.ServiceExceptionMapper;
import moneymanager.service.TransactionService;
import moneymanager.service.UserService;

public abstract class ServiceTest {
	
    protected static Server server = null;
    protected static PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
    protected static HttpClient client ;
    protected ObjectMapper mapper = new ObjectMapper();
    protected URIBuilder builder = new URIBuilder().setScheme("http").setHost("localhost:8080");


    @BeforeClass
    public static void setup() throws Exception {
        UserDAO.init();
        DebtsDAO.init();
        startServer();
        connManager.setDefaultMaxPerRoute(100);
        connManager.setMaxTotal(200);
        client= HttpClients.custom()
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();
    }

    @AfterClass
    public static void closeClient() throws Exception {
        HttpClientUtils.closeQuietly(client);
    }


    private static void startServer() throws Exception {
        if (server == null) {
            server = new Server(8080);
            ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
            context.setContextPath("/");
            server.setHandler(context);
            ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
            servletHolder.setInitParameter("jersey.config.server.provider.classnames",
                    UserService.class.getCanonicalName() + "," +
                            TransactionService.class.getCanonicalName() + "," +
                            CommonExpenseService.class.getCanonicalName() + "," 
                            +CommonProfitService.class.getCanonicalName());
            server.start();
        }
    }
}
