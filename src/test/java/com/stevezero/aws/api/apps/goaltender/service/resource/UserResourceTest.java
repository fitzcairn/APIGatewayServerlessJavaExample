package com.stevezero.aws.api.apps.goaltender.service.resource;

import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.MockContext;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.service.resource.impl.UserResource;
import com.stevezero.aws.api.apps.goaltender.service.user.handlers.PutUserHandler;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.IdentityType;
import com.stevezero.aws.api.service.MockStorageService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the public class UserResource.
 */
public class UserResourceTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";
  private final UserId testUserId = new UserId("1234", IdentityType.GOOGLE);
  private final UserResource testResource = new UserResource(
      testUserId,
      true,
      true,
      "2018-07-22T02:33:57+00:00",
      "2:33:57+00:00");


  // Test To/From JSON -->

  @Test
  public void testToJsonStringFull() {
    UserResource testResource = new UserResource(
        testUserId,
        true,
        true,
        "2018-07-22T02:33:57+00:00",
        "2:33:57+00:00");

    assertEquals("{\"reminderTimeString\":\"2:33:57+00:00\",\"lastUpdateDateTimeString\":\"2018-07-22T02:33:57+00:00\",\"hasRemindersOn\":true,\"hasSeenFtux\":true,\"userId\":\"eyJ0IjoiZyIsImkiOiIxMjM0In0=\"}", testResource.toJsonString());
  }

  @Test
  public void testToJsonStringPartial() {
    UserResource testResource = new UserResource(
        testUserId,
        true,
        true,
        null,
        null);

    assertEquals("{\"hasRemindersOn\":true,\"hasSeenFtux\":true,\"userId\":\"eyJ0IjoiZyIsImkiOiIxMjM0In0=\"}", testResource.toJsonString());
  }

  @Test
  public void testOfJsonStringFull() throws InvalidResourceIdException, InvalidApiResource {
    UserResource expectedResource = new UserResource(
        testUserId,
        true,
        true,
        "2018-07-22T02:33:57+00:00",
        "2:33:57+00:00");

    UserResource testResource = UserResource.of("{\"reminderTimeString\":\"2:33:57+00:00\",\"lastUpdateDateTimeString\":\"2018-07-22T02:33:57+00:00\",\"hasRemindersOn\":true,\"hasSeenFtux\":true,\"userId\":\"eyJ0IjoiZyIsImkiOiIxMjM0In0=\"}");
    assertEquals(expectedResource.toJsonString(), testResource.toJsonString());
  }

  @Test
  public void testOfJsonStringPartial() throws InvalidResourceIdException, InvalidApiResource {
    UserResource expectedResource = new UserResource(
        testUserId,
        true,
        true,
        null,
        null);

    UserResource testResource = UserResource.of("{\"hasRemindersOn\":true,\"hasSeenFtux\":true,\"userId\":\"eyJ0IjoiZyIsImkiOiIxMjM0In0=\"}");
    assertEquals(expectedResource.toJsonString(), testResource.toJsonString());
  }

  // Test To/From Item -->

  @Test
  public void testToItemFull() {
    UserResource testResource = new UserResource(
        testUserId,
        true,
        true,
        "2018-07-22T02:33:57+00:00",
        "2:33:57+00:00");

    UserItem expectedItem = new UserItem();
    expectedItem.setKey(testIdString);
    expectedItem.setHasRemindersOn(true);
    expectedItem.setHasSeenFtux(true);
    expectedItem.setLastUpdateDateTimeString("2018-07-22T02:33:57+00:00");
    expectedItem.setReminderTimeString("2:33:57+00:00");

    UserItem resultItem = (UserItem)testResource.toItem();
    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getLastUpdateDateTimeString(), resultItem.getLastUpdateDateTimeString());
    assertEquals(expectedItem.getReminderTimeString(), resultItem.getReminderTimeString());
    assertEquals(expectedItem.hasRemindersOn(), resultItem.hasRemindersOn());
    assertEquals(expectedItem.hasSeenFtux(), resultItem.hasSeenFtux());
  }

  @Test
  public void testToItemPartial() {
    UserResource testResource = new UserResource(
        testUserId,
        true,
        false,
        null,
        null);

    UserItem expectedItem = new UserItem();
    expectedItem.setKey(testIdString);
    expectedItem.setHasSeenFtux(true);

    UserItem resultItem = (UserItem)testResource.toItem();
    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getLastUpdateDateTimeString(), resultItem.getLastUpdateDateTimeString());
    assertEquals(expectedItem.getReminderTimeString(), resultItem.getReminderTimeString());
    assertEquals(expectedItem.hasRemindersOn(), resultItem.hasRemindersOn());
    assertEquals(expectedItem.hasSeenFtux(), resultItem.hasSeenFtux());
  }

  @Test
  public void testOfItemFull() throws InvalidResourceIdException {
    UserItem expectedItem = new UserItem();
    expectedItem.setKey(testIdString);
    expectedItem.setHasRemindersOn(true);
    expectedItem.setHasSeenFtux(true);
    expectedItem.setLastUpdateDateTimeString("2018-07-22T02:33:57+00:00");
    expectedItem.setReminderTimeString("2:33:57+00:00");

    UserResource testResource = UserResource.of(expectedItem);

    UserItem resultItem = (UserItem)testResource.toItem();
    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getLastUpdateDateTimeString(), resultItem.getLastUpdateDateTimeString());
    assertEquals(expectedItem.getReminderTimeString(), resultItem.getReminderTimeString());
    assertEquals(expectedItem.hasRemindersOn(), resultItem.hasRemindersOn());
    assertEquals(expectedItem.hasSeenFtux(), resultItem.hasSeenFtux());
  }

  @Test
  public void testOfItemPartial() throws InvalidResourceIdException {
    UserItem expectedItem = new UserItem();
    expectedItem.setKey(testIdString);
    expectedItem.setHasSeenFtux(true);
    expectedItem.setLastUpdateDateTimeString("2018-07-22T02:33:57+00:00");

    UserResource testResource = UserResource.of(expectedItem);

    UserItem resultItem = (UserItem)testResource.toItem();
    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getLastUpdateDateTimeString(), resultItem.getLastUpdateDateTimeString());
    assertEquals(expectedItem.getReminderTimeString(), resultItem.getReminderTimeString());
    assertEquals(expectedItem.hasRemindersOn(), resultItem.hasRemindersOn());
    assertEquals(expectedItem.hasSeenFtux(), resultItem.hasSeenFtux());
  }
}
