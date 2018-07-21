package com.stevezero.aws.api.apps.goaltender.service.resource.impl;

import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.service.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.service.resource.ApiResource;
import com.stevezero.aws.api.storage.items.MappedItem;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Immutable POJO representing a User API resource.
 */
public class UserResource implements ApiResource {
  private final UserId id;
  private final boolean hasSeenFtux;
  private final boolean hasRemindersOn;
  private final DateTime lastUpdateDateTime;
  private final DateTime reminderTime;

  private UserResource(UserId id,
                       boolean hasSeenFtux,
                       boolean hasRemindersOn,
                       DateTime lastUpdateDateTime,
                       DateTime reminderTime) {
    this.id = id;
    this.hasSeenFtux = hasSeenFtux;
    this.hasRemindersOn = hasRemindersOn;
    this.lastUpdateDateTime = lastUpdateDateTime;
    this.reminderTime = reminderTime;
  }

  /**
   * @return a JSON representation of this object, looking like:
   *   {
   *     userId: "id",
   *     lastUpdateDateTime: "ISO-8601 string",
   *     hasSeenFTUX: boolean,
   *     hasRemindersOn: boolean,
   *     reminderTime: "ISO-8601 string",
   *   }
   */
  @Override
  public String toJsonString() {
    JSONObject userJson = new JSONObject();
    userJson.put("id", id.toEncoded());
    if (lastUpdateDateTime != null) userJson.put("lastUpdateDateTime", lastUpdateDateTime.toDateTimeISO());
    userJson.put("hasSeenFTUX", hasSeenFtux);
    userJson.put("hasRemindersOn", hasRemindersOn);
    if (reminderTime != null) userJson.put("reminderTime", reminderTime.toDateTimeISO());
    return userJson.toJSONString();
  }

  @Override
  public MappedItem toItem() {
    return new UserItem();
  }

  public static UserResource fromUserItem(UserItem userItem) throws InvalidResourceIdException {
    // Without an ID, invalid resource.  TODO: probably need a better exception for this error, its a storage error.
    if (userItem.getUserId() == null) throw new InvalidResourceIdException("");

    return new UserResource(userItem.getUserId(),
        userItem.hasSeenFtux(),
        userItem.hasRemindersOn(),
        userItem.getLastUpdateDateTime(),
        userItem.getReminderTime());
  }

  /**
   * Parse from JSON into a UserResource.  Example body:
   *           {
   *             userId: String,
   *             lastUpdateDateTimeString: String,
   *             hasSeenFtux: boolean,
   *             hasRemindersOn: boolean,
   *             reminderTimeString: String,
   *           }
   *
   * @param jsonString
   * @return
   * @throws InvalidApiResource
   */
  public static UserResource fromJson(String jsonString, UserId id) throws InvalidApiResource {
    JSONParser jsonParser = new JSONParser();

    try {
      JSONObject json = (JSONObject) jsonParser.parse(jsonString);

      String userId = (String)json.get("userId");
      if (!userId.equals(id.toEncoded()))
        throw new ParseException(0);

      return new UserResource(id,
          (Boolean)json.get("hasSeenFtux"),
          (Boolean)json.get("hasRemindersOn"),
          new DateTime((String)json.get("lastUpdateDateTimeString")),
          new DateTime((String)json.get("reminderTimeString")));
    } catch (ParseException e) {
      throw new InvalidApiResource(ResourceType.USER.toString());
    }
  }
}
