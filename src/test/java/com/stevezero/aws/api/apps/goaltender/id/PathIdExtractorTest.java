package com.stevezero.aws.api.apps.goaltender.id;

import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.resource.ResourceType;
import com.stevezero.aws.api.exceptions.InvalidApiResourceName;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.id.IdentityType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for PathIdExtractor methods.
 */
public class PathIdExtractorTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";

  @Test
  public void testPathOk() throws InvalidApiResourceName, InvalidResourceIdException {
    ApiResourceId result = PathIdExtractor.extractIdFromPath(
        "/" + ResourceType.USER.toString() + "/" + testIdString,
         ResourceType.USER);

    UserId id = new UserId(result.toBase64String());
    assertEquals("1234", id.getId());
    assertEquals(IdentityType.GOOGLE, id.getType());
  }

  @Test(expected = InvalidApiResourceName.class)
  public void testPathInvalidResource() throws InvalidApiResourceName, InvalidResourceIdException {
    ApiResourceId result = PathIdExtractor.extractIdFromPath(
        "/notauser/" + testIdString,
        ResourceType.USER);
  }

  @Test(expected = InvalidApiResourceName.class)
  public void testPathInvalidPath() throws InvalidApiResourceName, InvalidResourceIdException {
    ApiResourceId result = PathIdExtractor.extractIdFromPath(
        "/prod/user/" + testIdString,
        ResourceType.USER);
  }
}