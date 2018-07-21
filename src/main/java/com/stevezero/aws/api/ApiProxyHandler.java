package com.stevezero.aws.goaltender.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.goaltender.common.ResourceType;
import com.stevezero.aws.api.exceptions.APIException;
import com.stevezero.aws.api.exceptions.impl.InvalidAPIMethod;
import com.stevezero.aws.goaltender.storage.service.StorageService;

import java.util.EnumMap;
import java.util.Map;

/**
 * Centralize logic common across all APIs.
 */
public abstract class ApiProxyHandler implements RequestHandler<ApiGatewayProxyRequest, ApiGatewayProxyResponse> {
  protected final StorageService storageService;
  protected final ResourceType resourceType;
  protected final Map<MethodType, ApiMethodHandler> methodHandlers = new EnumMap<MethodType, ApiMethodHandler>(MethodType.class);

  protected ApiProxyHandler(StorageService storageService, ResourceType resourceType) {
    this.storageService = storageService;
    this.resourceType = resourceType;
    this.init();
  }

  /**
   * Set up API-specific handlers.
   */
  protected abstract void init();

  /**
   * Handle the request for a proxy API.
   * @param request The request object.
   * @param context The lambda execution context.
   * @return an ApiGatewayProxyResponse filled with the API method invocation response.
   */
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request, Context context) {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    logger.log("Loading Java Lambda handler of UserProxyHandler");
    logger.log(request.toString());

    try {
      // Do we have this method implemented on this resource?
      ApiMethodHandler handler = methodHandlers.get(MethodType.valueOf(request.getHttpMethod()));
      if (handler == null)
        throw new InvalidAPIMethod(request.getHttpMethod());

      // Invoke the method handler
      return methodHandlers.get(resourceType).handle(request, context, storageService);
    } catch(APIException e) { // Catch API exceptions.
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
