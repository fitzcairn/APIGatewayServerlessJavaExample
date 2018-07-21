package com.stevezero.aws.api.service.resource;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Defines an API method handler.
 */
public interface ApiMethodHandler {

  /**
   * Handles a method call on the API.
   * @param request the API request.
   * @param context the lambda execution context.
   * @param storageService the underlying storage service.
   * @return
   */
   public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                                Context context,
                                                StorageService storageService);

}
