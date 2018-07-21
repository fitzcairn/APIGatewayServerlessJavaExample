package com.stevezero.aws.goaltender.api.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stevezero.aws.goaltender.api.ApiGatewayProxyRequest;
import com.stevezero.aws.goaltender.api.ApiGatewayProxyResponse;
import com.stevezero.aws.goaltender.api.exceptions.InvalidAPIResource;
import com.stevezero.aws.goaltender.api.exceptions.InvalidUserIdException;
import com.stevezero.aws.goaltender.api.user.methods.GetUser;
import com.stevezero.aws.goaltender.api.exceptions.APIException;
import com.stevezero.aws.goaltender.api.exceptions.InvalidAPIMethod;
import com.stevezero.aws.goaltender.api.http.Method;
import com.stevezero.aws.goaltender.api.http.StatusCode;
import com.stevezero.aws.goaltender.common.UserId;
import org.json.simple.parser.JSONParser;

/**
 * Entry point for handling methods for the User API.
 */
public class UserProxyHandler implements RequestHandler<ApiGatewayProxyRequest, ApiGatewayProxyResponse> {
  private static final String RESOURCE_NAME = "user";

  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request, Context context) {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    logger.log("Loading Java Lambda handler of UserProxyHandler");
    logger.log(request.toString());

    try {
      // Pull out the ID.
      UserId id = extractIdFromPath(request.getPath());

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
    } catch(APIException e) {
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

  // Visible for testing.
  public UserId extractIdFromPath(String idPathString)
      throws InvalidAPIResource, InvalidUserIdException {
    // Expect: /user/ID
    String[] components = idPathString.split("/");

    // Make sure we have expected input.
    if (components.length != 3 || !RESOURCE_NAME.equals(components[1]))
      throw new InvalidAPIResource("Invalid API call.");

    return UserId.fromEncoded(components[2]);
  }
}
