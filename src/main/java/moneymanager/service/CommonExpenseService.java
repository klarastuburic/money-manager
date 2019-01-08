package moneymanager.service;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.CommonExpense;
import moneymanager.model.User;
import moneymanager.model.UserTransaction;

@Path("/expense")
@Produces(MediaType.APPLICATION_JSON)
public class CommonExpenseService {
		
	private static Logger log = Logger.getLogger(TransactionService.class);
		
	@PUT
	@Path("")
	public Response newTransaction(CommonExpense expense) throws CustomException {
		expense.divideCost();
		return Response.status(Response.Status.OK).build();
	}
}
