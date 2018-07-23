package com.stevezero.aws.api.service.method;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;

/**
 * Defines an API method handler.
 */
public interface ApiMethodHandler {

  /**
   * Handles a method call on the API.
   * @param request the API request.
   * @param parsedApiPath the parsed API path with resource IDs.
   * @param context the lambda execution context.
   * @return
   */
   public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                                ApiPath parsedApiPath,
                                                Context context) throws ApiException;

}
