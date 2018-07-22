package com.stevezero.aws.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stevezero.aws.api.apps.goaltender.service.resource.ResourceType;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiMethod;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.service.ApiMethodHandler;
import com.stevezero.aws.api.storage.service.StorageService;

import java.util.EnumMap;
import java.util.Map;

/**
 * Generic AWS API Gatreway proxy API handler for lambdas.  Subclass this and add method-specific handlers to implement
 * a proxy API.
 */
public abstract class ApiProxyHandler implements RequestHandler<ApiGatewayProxyRequest, ApiGatewayProxyResponse> {
  protected final StorageService storageService;
  protected final ResourceType resourceType;
  protected final Map<MethodType, ApiMethodHandler> methodHandlers = new EnumMap<MethodType, ApiMethodHandler>(MethodType.class);

  protected ApiProxyHandler(StorageService storageService, ResourceType resourceType) {
    this.storageService = storageService;
    this.resourceType = resourceType;
    this.createHandlers();
  }

  /**
   * Assign specific handlers for HTTP methods.
   */
  protected abstract void createHandlers();

  /**
   * Handle the request for a proxy API.
   * @param request The request object.
   * @param context The lambda execution context.
   * @return an ApiGatewayProxyResponse filled with the API method invocation response.
   */
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request, Context context) {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    // TODO: remove.
    logger.log(request.toString());

    try {
      // Do we have this method implemented on this resource?
      ApiMethodHandler handler = methodHandlers.get(MethodType.valueOf(request.getHttpMethod()));
      if (handler == null)
        throw new InvalidApiMethod(request.getHttpMethod());

      // Invoke the method handler
      return methodHandlers.get(resourceType).handleRequest(request, context, storageService);
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
