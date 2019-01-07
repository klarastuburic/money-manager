package moneymanager.service;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.User;
import moneymanager.model.UserTransaction;



@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {
	
	private static Logger log = Logger.getLogger(TransactionService.class);
	
	@PUT
	@Path("/new")
    public Response newTransaction(UserTransaction transaction) throws CustomException {
		transaction.reduceBalance(transaction.getFromUserId());
		transaction.expendBalance(transaction.getToUserId());
		return Response.status(Response.Status.OK).build();
    }
}
