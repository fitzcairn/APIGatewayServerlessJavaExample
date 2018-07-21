package com.stevezero.aws.goaltender.api.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stevezero.aws.goaltender.api.ApiGatewayProxyRequest;
import com.stevezero.aws.goaltender.api.ApiGatewayProxyResponse;
import com.stevezero.aws.goaltender.api.user.data.UserId;
import com.stevezero.aws.goaltender.api.user.methods.GetUser;
import com.stevezero.aws.goaltender.api.exceptions.GoalTenderAPIException;
import com.stevezero.aws.goaltender.api.exceptions.InvalidAPIMethod;
import com.stevezero.aws.goaltender.api.http.Method;
import com.stevezero.aws.goaltender.api.http.StatusCode;
import org.json.simple.parser.JSONParser;

/**
 * Entry point for handling methods for the User API.
 */
public class UserProxyHandler implements RequestHandler<ApiGatewayProxyRequest, ApiGatewayProxyResponse> {
  private final JSONParser parser = new JSONParser();

  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request, Context context) {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    logger.log("Loading Java Lambda handler of UserProxyHandler");
    logger.log(request.toString());

    try {
      // Pull out the ID.
      UserId id = UserId.fromPathString(request.getPath());

      // Hand off to the correct handler, based on method.
      switch(Method.valueOf(request.getHttpMethod())) {
        case GET:
          return new GetUser().handle(id, context);
        case PUT:
          // TODO: implement
        case POST:
          // TODO: implement
        default:
          // No handler for method.
          throw new InvalidAPIMethod(request.getHttpMethod());
      }
    } catch(GoalTenderAPIException e) {
      return responseBuilder
          .withStatusCode(e.getReturnCode())
          .withBody(e.toString())
          .build();
    } catch(RuntimeException e) {  // Catch-all: something else went wrong.
      logger.log("Error: " + e.toString());
      return responseBuilder
          .withStatusCode(StatusCode.SERVER_ERROR)
          .build();
    }
  }
}
