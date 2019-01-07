package moneymanager.service;

import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.User;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
    
	private static Logger log = Logger.getLogger(UserService.class);

    @GET
    @Path("/all")
    public List<User> getAllUsers() throws CustomException {
        List<User> users = UserDAO.getAllUsers();
        return users;
    }
    
    @GET
    @Path("/{userId}")
    public User getUser(@PathParam("userId")int userId) throws CustomException {
    	return UserDAO.getUserById(userId);
    }
    

    @PUT
    @Path("/add")
    public User addUser(User user) throws CustomException {
        return UserDAO.addUser(user);
    }
    
    @PUT
    @Path("/{userId}")
    public void updateUser(@PathParam("userId") int userId, User user) throws CustomException {
        UserDAO.updateUser(userId, user.getUserName(), user.getEmailAddress());
    }
    
    /**
     * Delete by User Id
     * @param userId
     * @return
     * @throws CustomException
     */
    @DELETE
    @Path("/{userId}")
    public Response deleteUser(@PathParam("userId") int userId) throws CustomException {
        UserDAO.deleteUser(userId);
    	return Response.status(Response.Status.OK).build();
       
    }


}
