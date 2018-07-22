package com.stevezero.aws.api.apps.goaltender.service.resource.impl;

import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.service.resource.ResourceType;
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
public class UserResource implements ApiResource {
  private final UserId id;
  private final boolean hasSeenFtux;
  private final boolean hasRemindersOn;
  private final String lastUpdateDateTimeString;
  private final String reminderTimeString;

  public UserResource(UserId id,
                       boolean hasSeenFtux,
                       boolean hasRemindersOn,
                       String lastUpdateDateTimeString,
                       String reminderTimeString) {
    this.id = id;
    this.hasSeenFtux = hasSeenFtux;
    this.hasRemindersOn = hasRemindersOn;
    this.lastUpdateDateTimeString = lastUpdateDateTimeString;
    this.reminderTimeString = reminderTimeString;
  }

  /**
   * @return a JSON representation of this object, looking like:
   *   {
   *     userId: "id",
   *     lastUpdateDateTimeString: "ISO-8601 string",
   *     hasSeenFtux: boolean,
   *     hasRemindersOn: boolean,
   *     reminderTimeString: "ISO-8601 string",
   *   }
   */
  @Override
  public String toJsonString() {
    JSONObject userJson = new JSONObject();
    userJson.put("userId", id.toEncoded());
    if (lastUpdateDateTimeString != null) userJson.put("lastUpdateDateTimeString", lastUpdateDateTimeString);
    userJson.put("hasSeenFtux", hasSeenFtux);
    userJson.put("hasRemindersOn", hasRemindersOn);
    if (reminderTimeString != null) userJson.put("reminderTimeString", reminderTimeString);
    return userJson.toJSONString();
  }

  @Override
  public MappedItem toItem() {
    UserItem item = new UserItem();
    item.setKey(id.toEncoded());
    item.setLastUpdateDateTimeString(lastUpdateDateTimeString);
    item.setHasSeenFtux(hasSeenFtux);
    item.setHasRemindersOn(hasRemindersOn);
    item.setReminderTimeString(reminderTimeString);
    return item;
  }

  @Override
  public ResourceId getId() {
    return this.id;
  }

  public static UserResource of(UserItem userItem) throws InvalidResourceIdException {
    // Without an ID, invalid resource.  TODO: probably need a better exception for this error, its a storage error.
    if (userItem.getUserId() == null) throw new InvalidResourceIdException("");

    return new UserResource(userItem.getUserId(),
        userItem.hasSeenFtux(),
        userItem.hasRemindersOn(),
        userItem.getLastUpdateDateTimeString(),
        userItem.getReminderTimeString());
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
   * @throws InvalidResourceIdException when the ID is corrupted and undecodable.
   * @throws InvalidApiResource if the JSON is malformed.
   */
  public static UserResource of(String jsonString) throws InvalidResourceIdException, InvalidApiResource {
    JSONParser jsonParser = new JSONParser();

    try {
      JSONObject json = (JSONObject) jsonParser.parse(jsonString);

      String userId = (String)json.get("userId");
      if (userId == null)
        throw new ParseException(0);

      return new UserResource(UserId.fromEncoded(userId),
          (Boolean)json.get("hasSeenFtux"),
          (Boolean)json.get("hasRemindersOn"),
          (String)json.get("lastUpdateDateTimeString"),
          (String)json.get("reminderTimeString"));
    } catch (ParseException e) {
      throw new InvalidApiResource(ResourceType.USER);
    }
  }
}
