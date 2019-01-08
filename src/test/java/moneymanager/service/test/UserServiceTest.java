package moneymanager.service.test;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.server.Server;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import moneymanager.model.User;



public class UserServiceTest extends ServiceTest{
    
    
    @Test
    public void testGetUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/2").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);

        String jsonString = EntityUtils.toString(response.getEntity());
        User user = mapper.readValue(jsonString, User.class);
        assertTrue(user.getUserName().equals("Ivana"));
        assertTrue(user.getEmailAddress().equals("ivana@ivanic@gmail.com"));

    }
    
    @Test
    public void testGetAllUsers() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/all").build();
        HttpGet request = new HttpGet(uri);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        User[] users = mapper.readValue(jsonString, User[].class);
        assertTrue(users.length > 0);
    }
    
    @Test
    public void testaddUser() throws IOException, URISyntaxException {
    	User user = new User(7,"Matea", "matea@gmail.com");
    	URI uri = builder.setPath("/user/add").build();
        String jsonInString = mapper.writeValueAsString(user);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 200);
        String jsonString = EntityUtils.toString(response.getEntity());
        User user2 = mapper.readValue(jsonString, User.class);
        assertTrue(user2.getUserName().equals("Matea"));
        assertTrue(user2.getEmailAddress().equals("matea@gmail.com"));
    }
    
    @Test
    public void testAddExistingUser() throws IOException, URISyntaxException {
        URI uri = builder.setPath("/user/create").build();
        User user = new User(1,"Marko", "marko@markic.com");
        String jsonInString = mapper.writeValueAsString(user);
        StringEntity entity = new StringEntity(jsonInString);
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-type", "application/json");
        request.setEntity(entity);
        HttpResponse response = client.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertTrue(statusCode == 404);

    }
}
