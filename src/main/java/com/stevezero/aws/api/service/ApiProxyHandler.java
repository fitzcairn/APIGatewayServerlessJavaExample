package com.stevezero.aws.api.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.service.method.ApiMethodHandler;

/**
 * Generic AWS API Gateway proxy API handler for lambdas.  Subclass this and add method-specific handlers to implement
 * a proxy API.
 */
public abstract class ApiProxyHandler <T extends Enum>
    implements RequestHandler<ApiGatewayProxyRequest, ApiGatewayProxyResponse> {
  private final Class<T> resourceTypesClass;

  protected ApiProxyHandler(Class<T> resourceTypesClass) {
    this.resourceTypesClass = resourceTypesClass;
  }

  /**
   * Get the handler for a particular call, lazily constructed.
   */
  protected abstract ApiMethodHandler getMethodHandler(MethodType methodType, ApiPath<T> parsedApiPath)
    throws ApiException;

  /**
   * Handle the request for a proxy API.
   * @param request The request object.
   * @param context The lambda execution context.
   * @return an ApiGatewayProxyResponse filled with the API method invocation response.
   */
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request, Context context) {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    try {
      // Parse the path.
      ApiPath<T> parsedApiPath = ApiPath.of(request.getPath(), this.resourceTypesClass);

      // Do we have handlers for the last resource?
      ApiMethodHandler handler = getMethodHandler(MethodType.valueOf(request.getHttpMethod()), parsedApiPath);

      // Invoke the method handler
      return handler.handleRequest(request, parsedApiPath, context);

    } catch(ApiException e) { // Catch API exceptions.
      return responseBuilder
          .withStatusCode(e.getReturnCode())
          .withBody(e.toString())
          .build();
    } catch(RuntimeException e) {  // Catch-all: something else went wrong, return server error.
      logger.log("Error: " + e.toString());
      return responseBuilder
          .withStatusCode(StatusCode.SERVER_ERROR)
          .build();
    }
  }
}
