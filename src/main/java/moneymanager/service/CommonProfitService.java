package moneymanager.service;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import moneymanager.exception.CustomException;
import moneymanager.model.CommonExpense;
import moneymanager.model.CommonProfit;

@Path("/profit")
@Produces(MediaType.APPLICATION_JSON)
public class CommonProfitService {
	private static Logger log = Logger.getLogger(TransactionService.class);
	
	@PUT
	@Path("")
	public Response newProfit(CommonProfit profit) throws CustomException {
		log.info("***NEW PROFIT***");
		profit.divideProfit();
		return Response.status(Response.Status.OK).build();
	}
}
