package com.stevezero.aws.api.apps.goaltender.resource.impl;

import com.stevezero.aws.api.apps.goaltender.id.impl.GoalId;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.GoalItem;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoalResourceTest {
  // {"t": "g","i": "1234"}
  private final String userIdString = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";
  private final String goalIdString = "abcd";
  private final GoalId testGoalId = new GoalId(goalIdString, userIdString);


  /**
   * Constructor Tests -->
   */

  @Test(expected = AssertionError.class)
  public void testConstructNoTextInvalid() {
    GoalResource testResource = new GoalResource(
        testGoalId,
        null,
        "2018-07-22T02:33:57+00:00",
        false);
  }

  @Test(expected = AssertionError.class)
  public void testConstructgNoDateInvalid() {
    GoalResource testResource = new GoalResource(
        testGoalId,
        "Goal Text",
        null,
        false);
  }

  @Test(expected = AssertionError.class)
  public void testConstructNoIdInvalid() {
    GoalResource testResource = new GoalResource(
        null,
        "Goal Text",
        "2018-07-22T02:33:57+00:00",
        false);
  }


  /**
   * JSON Tests -->
   */

  @Test
  public void testToJsonStringFull() {
    GoalResource testResource = new GoalResource(
        testGoalId,
        "Goal Text",
        "2018-07-22T02:33:57+00:00",
        true);

    assertEquals("{\"goalId\":\"abcd\",\"goalCreateDateString\":\"2018-07-22T02:33:57+00:00\",\"goalText\":\"Goal Text\",\"complete\":true}", testResource.toJsonString());
  }

  @Test
  public void testToJsonStringNotComplete() {
    GoalResource testResource = new GoalResource(
        testGoalId,
        "Goal Text",
        "2018-07-22T02:33:57+00:00",
        false);

    assertEquals("{\"goalId\":\"abcd\",\"goalCreateDateString\":\"2018-07-22T02:33:57+00:00\",\"goalText\":\"Goal Text\",\"complete\":false}", testResource.toJsonString());
  }

  @Test
  public void testUserOfJsonStringFull() throws InvalidResourceIdException, InvalidApiResource {
    GoalResource expectedResource = new GoalResource(
        testGoalId,
        "Goal Text",
        "2018-07-22T02:33:57+00:00",
        true);

    GoalResource testResource = new GoalResource("{\"goalId\":\"abcd\",\"goalCreateDateString\":\"2018-07-22T02:33:57+00:00\",\"goalText\":\"Goal Text\",\"complete\":true}", userIdString);
    assertEquals(expectedResource.toJsonString(), testResource.toJsonString());
  }

  public void testUserOfJsonStringNotComplete() throws InvalidResourceIdException, InvalidApiResource {
    GoalResource expectedResource = new GoalResource(
        testGoalId,
        "Goal Text",
        "2018-07-22T02:33:57+00:00",
        false);

    GoalResource testResource = new GoalResource("{\"goalId\":\"abcd\",\"goalCreateDateString\":\"2018-07-22T02:33:57+00:00\",\"goalText\":\"Goal Text\",\"complete\":false}", userIdString);
    assertEquals(expectedResource.toJsonString(), testResource.toJsonString());
  }


  /**
   * Item Tests -->
   */
  @Test
  public void testToItemFull() {
    GoalResource testResource = new GoalResource(
        testGoalId,
        "Goal Text",
        "2018-07-22T02:33:57+00:00",
        true);

    GoalItem expectedItem = new GoalItem();
    expectedItem.setKey(testGoalId.toBase64String());
    expectedItem.setGoalText("Goal Text");
    expectedItem.setGoalCreateDateString("2018-07-22T02:33:57+00:00");
    expectedItem.setComplete(true);

    GoalItem resultItem = (GoalItem)testResource.toItem();
    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getGoalCreateDateString(), resultItem.getGoalCreateDateString());
    assertEquals(expectedItem.getGoalText(), resultItem.getGoalText());
    assertEquals(expectedItem.getComplete(), resultItem.getComplete());
  }

  @Test
  public void testToItemNotComplete() {
    GoalResource testResource = new GoalResource(
        testGoalId,
        "Goal Text",
        "2018-07-22T02:33:57+00:00",
        false);

    GoalItem expectedItem = new GoalItem();
    expectedItem.setKey(testGoalId.toBase64String());
    expectedItem.setGoalText("Goal Text");
    expectedItem.setGoalCreateDateString("2018-07-22T02:33:57+00:00");
    expectedItem.setComplete(false);

    GoalItem resultItem = (GoalItem)testResource.toItem();
    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getGoalCreateDateString(), resultItem.getGoalCreateDateString());
    assertEquals(expectedItem.getGoalText(), resultItem.getGoalText());
    assertEquals(expectedItem.getComplete(), resultItem.getComplete());
  }


  @Test
  public void testOfItemFull() throws InvalidResourceIdException {
    GoalItem expectedItem = new GoalItem();
    expectedItem.setKey(testGoalId.toBase64String());
    expectedItem.setGoalText("Goal Text");
    expectedItem.setGoalCreateDateString("2018-07-22T02:33:57+00:00");
    expectedItem.setComplete(true);

    GoalResource testResource = new GoalResource(expectedItem);
    GoalItem resultItem = (GoalItem)testResource.toItem();

    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getGoalCreateDateString(), resultItem.getGoalCreateDateString());
    assertEquals(expectedItem.getGoalText(), resultItem.getGoalText());
    assertEquals(expectedItem.getComplete(), resultItem.getComplete());
  }

  public void testOfItemNotComplete() throws InvalidResourceIdException {
    GoalItem expectedItem = new GoalItem();
    expectedItem.setKey(testGoalId.toBase64String());
    expectedItem.setGoalText("Goal Text");
    expectedItem.setGoalCreateDateString("2018-07-22T02:33:57+00:00");
    expectedItem.setComplete(false);

    GoalResource testResource = new GoalResource(expectedItem);
    GoalItem resultItem = (GoalItem)testResource.toItem();

    assertEquals(expectedItem.getKey(), resultItem.getKey());
    assertEquals(expectedItem.getGoalCreateDateString(), resultItem.getGoalCreateDateString());
    assertEquals(expectedItem.getGoalText(), resultItem.getGoalText());
    assertEquals(expectedItem.getComplete(), resultItem.getComplete());
  }
}
