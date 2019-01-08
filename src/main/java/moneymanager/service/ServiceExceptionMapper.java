package moneymanager.service;

import moneymanager.exception.CustomException;
import moneymanager.exception.ErrorResponse;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServiceExceptionMapper implements ExceptionMapper<CustomException> {

	public ServiceExceptionMapper() {
	}

	public Response toResponse(CustomException daoException) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setErrorCode(daoException.getMessage());

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
	}

}
