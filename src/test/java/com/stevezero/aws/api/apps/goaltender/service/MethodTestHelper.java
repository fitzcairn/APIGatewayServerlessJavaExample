package com.stevezero.aws.api.apps.goaltender.service;


import com.stevezero.aws.api.apps.goaltender.id.impl.GoalId;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.service.method.GoalTenderApiMethodHandler;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.id.IdentityType;
import com.stevezero.aws.api.mocks.MockContext;
import com.stevezero.aws.api.mocks.MockStorageService;
import com.stevezero.aws.api.resource.ApiResource;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Before;

/**
 * Simplify tests by removing boilerplate.
 */
public abstract class MethodTestHelper {
  // Static across all tests.
  protected final UserId testUserId = new UserId("1234", IdentityType.GOOGLE);
  protected final String testUserIdString = testUserId.toBase64String();
  protected final GoalId testGoalId = new GoalId("abcd", testUserId);
  protected final MockStorageService storageService = new MockStorageService();

  // Set per-test, in test constructor.
  protected MockContext context;
  protected GoalTenderApiMethodHandler requestHandler;
  protected ApiGatewayProxyRequest testRequest;
  protected MethodType testMethod;

  @Before
  public void reset() {
    context = new MockContext();
    storageService.reset();
    testRequest = new ApiGatewayProxyRequest()
        .setHttpMethod(testMethod.toString())
        .setBase64Encoded(false);
  }

  protected ApiGatewayProxyResponse getOutput(ApiPath apiPath, ApiResource resource) throws ApiException {
    testRequest.setPath(apiPath.getPath());
    testRequest.setBody(resource.toJsonString());
    return requestHandler.handleRequest(testRequest, apiPath, context);
  }

  protected ApiGatewayProxyResponse getOutput(ApiPath apiPath) throws ApiException {
    testRequest.setPath(apiPath.getPath());
    return requestHandler.handleRequest(testRequest, apiPath, context);
  }
}
