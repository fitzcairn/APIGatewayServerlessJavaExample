package com.stevezero.aws.api.goaltender.service.user;

import com.stevezero.aws.api.goaltender.id.impl.UserId;
import com.stevezero.aws.api.goaltender.storage.items.impl.UserItem;
import org.joda.time.DateTime;
import org.json.simple.JSONObject;

/**
 * Immutable POJO representing a user.
 */
public class User {
  private final UserId id;
  private final boolean hasSeenFtux;
  private final boolean hasRemindersOn;
  private final DateTime lastUpdateDateTime;
  private final DateTime reminderTime;

  private User(UserId id,
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
  public String toJsonString() {
    JSONObject userJson = new JSONObject();
    userJson.put("id", id.toEncoded());
    userJson.put("lastUpdateDateTime", lastUpdateDateTime.toDateTimeISO());
    userJson.put("hasSeenFTUX", hasSeenFtux);
    userJson.put("hasRemindersOn", hasRemindersOn);
    userJson.put("reminderTime", reminderTime.toDateTimeISO());
    return userJson.toJSONString();
  }

  public static User fromUserItem(UserItem userItem) {
    return new User(userItem.getUserId(),
        userItem.hasSeenFtux(),
        userItem.hasRemindersOn(),
        userItem.getLastUpdateDateTime(),
        userItem.getReminderTime());
  }
}
