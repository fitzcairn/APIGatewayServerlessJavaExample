package com.stevezero.aws.api.apps.goaltender.resource.impl;

import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.id.IdentityType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the public class UserResource.
 */
public class UserResourceTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";
  private final UserId testUserId = new UserId("1234", IdentityType.GOOGLE);

  /**
   * Constructor Tests -->
   */

  @Test(expected = AssertionError.class)
  public void testConstructNoIdInvalid() {
    UserResource testResource = new UserResource(
        null,
        true,
        true,
        "2018-07-22T02:33:57+00:00",
        "2:33:57+00:00");
  }


  /**
   * JSON Tests -->
   */

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
  public void testUserOfJsonStringFull() throws InvalidResourceIdException, InvalidApiResource {
    UserResource expectedResource = new UserResource(
        testUserId,
        true,
        true,
        "2018-07-22T02:33:57+00:00",
        "2:33:57+00:00");

    UserResource testResource = new UserResource("{\"reminderTimeString\":\"2:33:57+00:00\",\"lastUpdateDateTimeString\":\"2018-07-22T02:33:57+00:00\",\"hasRemindersOn\":true,\"hasSeenFtux\":true,\"userId\":\"eyJ0IjoiZyIsImkiOiIxMjM0In0=\"}");
    assertEquals(expectedResource.toJsonString(), testResource.toJsonString());
  }

  @Test
  public void testUserOfJsonStringPartial() throws InvalidResourceIdException, InvalidApiResource {
    UserResource expectedResource = new UserResource(
        testUserId,
        true,
        true,
        null,
        null);

    UserResource testResource = new UserResource("{\"hasRemindersOn\":true,\"hasSeenFtux\":true,\"userId\":\"eyJ0IjoiZyIsImkiOiIxMjM0In0=\"}");
    assertEquals(expectedResource.toJsonString(), testResource.toJsonString());
  }


  /**
   * Item Tests -->
   */

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
    assertEquals(expectedItem.getHasRemindersOn(), resultItem.getHasRemindersOn());
    assertEquals(expectedItem.getHasSeenFtux(), resultItem.getHasSeenFtux());
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
    assertEquals(expectedItem.getHasRemindersOn(), resultItem.getHasRemindersOn());
    assertEquals(expectedItem.getHasSeenFtux(), resultItem.getHasSeenFtux());
  }

  @Test
  public void testUserOfItemFull() throws InvalidResourceIdException {
    UserItem expectedItem = new UserItem();
    expectedItem.setKey(testIdString);
    expectedItem.setHasRemindersOn(true);
    expectedItem.setHasSeenFtux(true);
    expectedItem.setLastUpdateDateTimeString("2018-07-22T02:33:57+00:00");
    expectedItem.setReminderTimeString("2:33:57+00:00");

    UserResource testResource = new UserResource(expectedItem);

    UserItem resultItem = (UserItem)testResource.toItem();
    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getLastUpdateDateTimeString(), resultItem.getLastUpdateDateTimeString());
    assertEquals(expectedItem.getReminderTimeString(), resultItem.getReminderTimeString());
    assertEquals(expectedItem.getHasRemindersOn(), resultItem.getHasRemindersOn());
    assertEquals(expectedItem.getHasSeenFtux(), resultItem.getHasSeenFtux());
  }

  @Test
  public void testUserOfItemPartial() throws InvalidResourceIdException {
    UserItem expectedItem = new UserItem();
    expectedItem.setKey(testIdString);
    expectedItem.setHasSeenFtux(true);
    expectedItem.setLastUpdateDateTimeString("2018-07-22T02:33:57+00:00");

    UserResource testResource = new UserResource(expectedItem);

    UserItem resultItem = (UserItem)testResource.toItem();
    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getLastUpdateDateTimeString(), resultItem.getLastUpdateDateTimeString());
    assertEquals(expectedItem.getReminderTimeString(), resultItem.getReminderTimeString());
    assertEquals(expectedItem.getHasRemindersOn(), resultItem.getHasRemindersOn());
    assertEquals(expectedItem.getHasSeenFtux(), resultItem.getHasSeenFtux());
  }
}
