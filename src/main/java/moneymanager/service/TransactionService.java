package moneymanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import moneymanager.dao.impl.DebtsDAO;
import moneymanager.dao.impl.UserDAO;
import moneymanager.exception.CustomException;
import moneymanager.model.Debts;
import moneymanager.model.User;
import moneymanager.model.UserTransaction;



@Path("/transaction")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionService {
	
	private static Logger log = Logger.getLogger(TransactionService.class);
	
	@PUT
	@Path("/new")
    public Response newTransaction(UserTransaction transaction) throws CustomException {
		log.info("*****NEW TRANSACTION*****");
		transaction.manageTransactions();
		return Response.status(Response.Status.OK).build();
    }
	
	@GET
	@Path("/currentstate")
	public Map<Integer,HashMap<Integer, Double>> getCurrentState ()   {
		return DebtsDAO.getAllDebts();
		
	}
}
