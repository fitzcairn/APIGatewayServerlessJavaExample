package com.stevezero.aws.api.apps.goaltender.resource.impl;

import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.resource.ApiResource;
import com.stevezero.aws.api.storage.items.MappedItem;
import org.json.simple.JSONObject;
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
    assert(id != null);

    this.id = id;
    this.hasSeenFtux = hasSeenFtux;
    this.hasRemindersOn = hasRemindersOn;
    this.lastUpdateDateTimeString = lastUpdateDateTimeString;
    this.reminderTimeString = reminderTimeString;
  }

  @Override
  public ApiResourceId getResourceId() {
    return this.id;
  }


  /**
   * Parse a backend UserItem into an ApiResource.
   * @param userItem the UserItem instance.
   * @throws InvalidResourceIdException on error parsing the Item key.
   */
  public UserResource(UserItem userItem) throws InvalidResourceIdException {
    // Without an ID, invalid resource.
    // TODO: probably need a better exception for this error, its a storage error.
    if (userItem.getKey() == null) throw new InvalidResourceIdException("");

    this.id = new UserId(userItem.getKey());
    this.hasSeenFtux = userItem.getHasSeenFtux();
    this.hasRemindersOn = userItem.getHasRemindersOn();
    this.lastUpdateDateTimeString = userItem.getLastUpdateDateTimeString();
    this.reminderTimeString = userItem.getReminderTimeString();
  }

  /**
   * @return a UserItem suitable for putting to storage.
   */
  @Override
  public MappedItem toItem() {
    UserItem item = new UserItem();
    item.setKey(id.toBase64String());
    item.setLastUpdateDateTimeString(lastUpdateDateTimeString);
    item.setHasSeenFtux(hasSeenFtux);
    item.setHasRemindersOn(hasRemindersOn);
    item.setReminderTimeString(reminderTimeString);
    return item;
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
   * @param jsonString the JSON representation of the resource.
   * @throws InvalidResourceIdException when the ID is corrupted and undecodable.
   * @throws InvalidApiResource if the JSON is malformed.
   */
  public UserResource(String jsonString) throws InvalidResourceIdException, InvalidApiResource {
    try {
      if (jsonString == null)
        throw new ParseException(0);

      JSONObject json = (JSONObject) JSON_PARSER.parse(jsonString);

      String userId = (String)json.get("userId");
      if (userId == null)
        throw new ParseException(0);

      this.id = new UserId(userId);
      this.hasSeenFtux = (Boolean)json.get("hasSeenFtux");
      this.hasRemindersOn = (Boolean)json.get("hasRemindersOn");
      this.lastUpdateDateTimeString = (String)json.get("lastUpdateDateTimeString");
      this.reminderTimeString = (String)json.get("reminderTimeString");

    } catch (ParseException e) {
      throw new InvalidApiResource(ResourceType.USER);
    }
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
    userJson.put("userId", id.toBase64String());
    if (lastUpdateDateTimeString != null) userJson.put("lastUpdateDateTimeString", lastUpdateDateTimeString);
    userJson.put("hasSeenFtux", hasSeenFtux);
    userJson.put("hasRemindersOn", hasRemindersOn);
    if (reminderTimeString != null) userJson.put("reminderTimeString", reminderTimeString);
    return userJson.toJSONString();
  }
}
