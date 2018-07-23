package com.stevezero.aws.api.apps.goaltender.resource.impl;

import com.stevezero.aws.api.apps.goaltender.id.impl.GoalId;
import com.stevezero.aws.api.apps.goaltender.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.GoalItem;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.resource.ApiResource;
import com.stevezero.aws.api.storage.items.MappedItem;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Immutable POJO representing a Goal API resource.
 */
public class GoalResource implements ApiResource {
  private final GoalId id;
  private final String goalText;
  private final String goalCreateDateString;
  private final boolean complete;

  public GoalResource(GoalId id,
                      String goalText,
                      String goalCreateDateString,
                      boolean complete) {
    assert(id != null && goalText != null && goalCreateDateString != null);

    this.id = id;
    this.goalText = goalText;
    this.goalCreateDateString = goalCreateDateString;
    this.complete = complete;

  }


  /**
   * Parse from JSON into a GoalResource.  Example body:
   *           {
   *             goalId: String,
   *             goalText: String,
   *             goalCreateDateString: String,
   *             complete: boolean,
\   *           }
   *
   * @param jsonString the JSON representation of the resource.
   * @param userBase64String the JSON representation of the resource.
   * @throws InvalidResourceIdException when the ID is corrupted and undecodable.
   * @throws InvalidApiResource if the JSON is malformed.
   */
  public GoalResource(String jsonString, String userBase64String)
      throws InvalidResourceIdException, InvalidApiResource {
    try {
      if (jsonString == null)
        throw new ParseException(0);

      JSONObject json = (JSONObject) JSON_PARSER.parse(jsonString);

      String goalId = (String)json.get("goalId");
      if (goalId == null)
        throw new ParseException(0);

      this.id = new GoalId(goalId, userBase64String);
      this.complete = (Boolean)json.get("complete");

      // Must have text and a create date.
      this.goalText = (String)json.get("goalText");
      if (goalText == null)
        throw new ParseException(0);
      this.goalCreateDateString = (String)json.get("goalCreateDateString");
      if (goalCreateDateString == null)
        throw new ParseException(0);

    } catch (ParseException e) {
      throw new InvalidApiResource(ResourceType.GOAL);
    }
  }

  /**
   * @return a JSON representation of this object, looking like:
   *   {
   *     goalId: "12",
   *     goalText: "Text",
   *     complete: false,
   *     goalCreateDateString: "2018-05-31", // ISO 8601, UTC, but day only.
   *   }
   */
  @Override
  public String toJsonString() {
    JSONObject userJson = new JSONObject();
    userJson.put("goalId", id.getGoalIdString());
    userJson.put("goalText", goalText);
    userJson.put("complete", complete);
    userJson.put("goalCreateDateString", goalCreateDateString);
    return userJson.toJSONString();
  }


  /**
   * Parse a backend UserItem into an ApiResource.
   * @param goalItem the GoalItem instance.
   * @throws InvalidResourceIdException on error parsing the Item key.
   */
  public GoalResource(GoalItem goalItem) throws InvalidResourceIdException {
    // Without an ID, invalid resource.
    // TODO: probably need a better exception for this error, its a storage error.
    if (goalItem.getKey() == null) throw new InvalidResourceIdException("");

    this.id = new GoalId(goalItem.getKey());
    this.complete = goalItem.getComplete();
    this.goalCreateDateString = goalItem.getGoalCreateDateString();
    this.goalText = goalItem.getGoalText();
  }

  @Override
  public MappedItem toItem() {
    GoalItem item = new GoalItem();
    item.setKey(id.toBase64String());
    item.setComplete(this.complete);
    item.setGoalCreateDateString(this.goalCreateDateString);
    item.setGoalText(this.goalText);
    return item;
  }

  @Override
  public ApiResourceId getResourceId() {
    return this.id;
  }
}
