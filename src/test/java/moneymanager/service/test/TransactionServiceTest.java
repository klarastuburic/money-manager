package moneymanager.service.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import moneymanager.dao.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.User;
import moneymanager.model.UserTransaction;

public class TransactionServiceTest extends ServiceTest {
	
	    @Test
	    public void testGetDebts() throws IOException, URISyntaxException, CustomException {
		 	UserTransaction trans = new UserTransaction(1,2,200);
		 	trans.manageTransactions();
	        URI uri = builder.setPath("/debts").build();
	        HttpGet request = new HttpGet(uri);
	        HttpResponse response = client.execute(request);
	        int statusCode = response.getStatusLine().getStatusCode();
	        assertTrue(statusCode == 200);
	        String jsonString = EntityUtils.toString(response.getEntity());
	        Map<Integer,HashMap<Integer,Double>> map  = mapper.readValue(jsonString, new TypeReference<Map<Integer,HashMap<Integer,Double>>>(){});
	        assertTrue(map.containsKey(2)); //2 owes to 1
	        assertTrue(map.get(2).containsKey(1));
	    }
	    
	    @Test
	    public void testAddTransaction() throws IOException, URISyntaxException, CustomException {
	    	UserDAO.getUserById(1).setAmount(0.0); //initialize amounts to 0
	    	UserDAO.getUserById(2).setAmount(0.0);
	    	UserTransaction trans = new UserTransaction(1,2,400);
	     	URI uri = builder.setPath("/transaction").build();
	        String jsonInString = mapper.writeValueAsString(trans);
	        StringEntity entity = new StringEntity(jsonInString);
	        HttpPut request = new HttpPut(uri);
	        request.setHeader("Content-type", "application/json");
	        request.setEntity(entity);
	        HttpResponse response = client.execute(request);
	        int statusCode = response.getStatusLine().getStatusCode();
	        assertTrue(statusCode == 200);
	        User user = UserDAO.getUserById(1);
	        assertTrue(user.getAmount() == 400); //check amounts on two users in transaction
	        User user2 = UserDAO.getUserById(2);
	        assertTrue(user2.getAmount() == -400);
	    }
}
