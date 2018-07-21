package com.stevezero.aws.api.apps.goaltender.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.exceptions.InvalidAPIResource;
import com.stevezero.aws.api.exceptions.InvalidUserIdException;
import com.stevezero.aws.api.id.IdentityType;
import com.stevezero.aws.api.id.ResourceId;
import com.stevezero.aws.api.storage.service.StorageService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for ApiMethodHandler methods.
 */
public class ApiMethodHandlerTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";

  private class TestApiMethodHandler extends ApiMethodHandler {
    @Override
    public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request, Context context, StorageService storageService) {
      return null;
    }
  }

  @Test
  public void testPathOk() throws InvalidAPIResource, InvalidUserIdException {
    ResourceId result = new TestApiMethodHandler().extractIdFromPath(
        "/" + ResourceType.USER.toString() + "/" + testIdString,
         ResourceType.USER);

    UserId id = UserId.fromEncoded(result.toEncoded());
    assertEquals("1234", id.getId());
    assertEquals(IdentityType.GOOGLE, id.getType());
  }

  @Test(expected = InvalidAPIResource.class)
  public void testPathInvalidResource() throws InvalidAPIResource, InvalidUserIdException {
    ResourceId result = new TestApiMethodHandler().extractIdFromPath(
        "/notauser/" + testIdString,
        ResourceType.USER);
  }

  @Test(expected = InvalidAPIResource.class)
  public void testPathInvalidPath() throws InvalidAPIResource, InvalidUserIdException {
    ResourceId result = new TestApiMethodHandler().extractIdFromPath(
        "/prod/user/" + testIdString,
        ResourceType.USER);
  }
}