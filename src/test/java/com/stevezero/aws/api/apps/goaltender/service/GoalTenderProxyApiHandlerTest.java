package com.stevezero.aws.api.apps.goaltender.service;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Simple test to ensure that all the resources we have listed actually have handlers.
 * Reminder when implementing new API endpoints.
 */
public class GoalTenderProxyApiHandlerTest {
  private final GoalTenderProxyApiHandler testHandler = new GoalTenderProxyApiHandler();

  @Test
  public void testResources() throws ApiException {
    // Every resource should have a GET and a PUT method in the GoalTender API.
    // The handler will blow up if not.
    for (GoalTenderResourceType resource : GoalTenderResourceType.values()) {
      assertNotNull(testHandler.getMethodHandler(MethodType.GET,
          ApiPath.of("/" + resource.toString(), GoalTenderResourceType.class)));
      assertNotNull(testHandler.getMethodHandler(MethodType.PUT,
          ApiPath.of("/" + resource.toString(), GoalTenderResourceType.class)));
    }
  }
}
