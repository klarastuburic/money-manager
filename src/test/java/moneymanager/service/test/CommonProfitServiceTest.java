package moneymanager.service.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

import moneymanager.dao.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.CommonProfit;
import moneymanager.model.User;

public class CommonProfitServiceTest extends ServiceTest{
	
	@Test
    public void testAddProfit() throws IOException, URISyntaxException, CustomException {
    	UserDAO.getUserById(1).setAmount(0.0); //initialize amounts to 0
    	UserDAO.getUserById(2).setAmount(0.0);
    	UserDAO.getUserById(3).setAmount(0.0);
    	
    	CommonProfit expense = new CommonProfit(1,"2 3",600);
     	URI uri = builder.setPath("/profit").build();
        String jsonInString = mapper.writeValueAsString(expense);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        
        User user = UserDAO.getUserById(1);
        assertTrue(user.getAmount() == -400); //check amounts on two users in transaction
        User user2 = UserDAO.getUserById(2);
        assertTrue(user2.getAmount() == 200);
        User user3 = UserDAO.getUserById(3);
        assertTrue(user3.getAmount() == 200);
    }
}
