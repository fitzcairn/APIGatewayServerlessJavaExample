package com.stevezero.aws.goaltender.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stevezero.aws.goaltender.ApiGatewayProxyRequest;
import com.stevezero.aws.goaltender.ApiGatewayProxyResponse;
import com.stevezero.aws.goaltender.StatusCode;
import com.stevezero.aws.goaltender.user.data.UserId;
import com.stevezero.aws.goaltender.user.methods.GetUser;
import org.json.simple.parser.JSONParser;

import java.io.IOException;

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

      logger.log(request.getHttpMethod());

      // Hand off to the correct handler, based on method.
      // TODO: yuck, let's do something smarter here?
      if (request.getHttpMethod().equals("GET")) {
        return new GetUser().handle(id, context);
      }
      // No handler for method.
      return responseBuilder
          .withStatusCode(StatusCode.NOT_ALLOWED)
          .build();

    } catch(IOException e) { // ID parse failed.
      return responseBuilder
          .withStatusCode(StatusCode.NOT_FOUND)
          .withBody(e.toString())
          .build();
    } catch(Exception e) {  // Something else went wrong.
      return responseBuilder
          .withStatusCode(StatusCode.SERVER_ERROR)
          .withBody(e.toString())
          .build();
    }
  }
}
