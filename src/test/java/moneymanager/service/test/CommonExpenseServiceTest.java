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
import moneymanager.model.CommonExpense;
import moneymanager.model.User;
import moneymanager.model.UserTransaction;

public class CommonExpenseServiceTest extends ServiceTest {
	
	@Test
    public void testAddExpense() throws IOException, URISyntaxException, CustomException {
    	UserDAO.getUserById(1).setAmount(0.0); //initialize amounts to 0
    	UserDAO.getUserById(2).setAmount(0.0);
    	UserDAO.getUserById(3).setAmount(0.0);
    	
    	CommonExpense expense = new CommonExpense(1,"2 3",300);
     	URI uri = builder.setPath("/expense").build();
        String jsonInString = mapper.writeValueAsString(expense);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        
        User user = UserDAO.getUserById(1);
        assertTrue(user.getAmount() == 200); //check amounts on two users in transaction
        User user2 = UserDAO.getUserById(2);
        assertTrue(user2.getAmount() == -100);
        User user3 = UserDAO.getUserById(3);
        assertTrue(user3.getAmount() == -100);
    }
}
