package com.stevezero.aws.api.apps.goaltender.resource.impl;

import com.stevezero.aws.api.apps.goaltender.id.impl.GoalId;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.GoalItem;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.id.ResourceId;
import com.stevezero.aws.api.service.resource.ApiResource;
import com.stevezero.aws.api.storage.items.MappedItem;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Immutable POJO representing a User API resource.
 */
public class GoalResource implements ApiResource {
  private final String id;

  public GoalResource(String id) {
    this.id = id;
  }

  /**
   * @return a JSON representation of this object, looking like:
   *   {
   *     goalId: "id",
   *   }
   */
  @Override
  public String toJsonString() {
    JSONObject userJson = new JSONObject();
    userJson.put("goalId", id);
    return userJson.toJSONString();
  }

  @Override
  public MappedItem toItem() {
    GoalItem item = new GoalItem();
    item.setKey(id);
    return item;
  }

  @Override
  public ResourceId getId() {
    // TODO: implement
    return new GoalId(this.id);
  }

  public static GoalResource of(GoalItem goalItem) throws InvalidResourceIdException {
    // Without an ID, invalid resource.
    if (goalItem.getKey() == null) throw new InvalidResourceIdException("");

    return new GoalResource(goalItem.getKey());
  }

  /**
   * Parse from JSON into a UserResource.  Example body:
   *           {
   *             goalId: String,
   *           }
   *
   * @param jsonString the encoded resource.
   * @return GoalResource a full resource POJO.
   * @throws InvalidApiResource if the JSON is malformed.
   */
  public static GoalResource of(String jsonString) throws InvalidApiResource {
    JSONParser jsonParser = new JSONParser();

    try {
      if (jsonString == null)
        throw new ParseException(0);

      JSONObject json = (JSONObject) jsonParser.parse(jsonString);

      String goalId = (String)json.get("goalId");
      if (goalId == null)
        throw new ParseException(0);

      return new GoalResource(goalId);
    } catch (ParseException e) {
      throw new InvalidApiResource(ResourceType.GOAL);
    }
  }
}
