package com.stevezero.aws.goaltender.storage.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.stevezero.aws.goaltender.common.IdentityType;
import org.joda.time.DateTime;

/**
 * Pojo for a UserRecord in Dynamo.
 */
@DynamoDBTable(tableName="goaltender-user")
public class UserRecord implements Record {
  private final String id;
  private final IdentityType type;
  private final String lastUpdateDateTimeString;
  private DateTime lastUpdateDateTime;
  private final boolean hasSeenFtux;
  private final boolean hasRemindersOn;
  private final String reminderTimeString;
  private DateTime reminderTime;

  private UserRecord(String id,
                     IdentityType type,
                     String lastUpdateDateTime,
                     boolean hasSeenFtux,
                     boolean hasRemindersOn,
                     String reminderTime) {
    this.id = id;
    this.type = type;
    this.lastUpdateDateTimeString = lastUpdateDateTime;
    this.hasSeenFtux = hasSeenFtux;
    this.hasRemindersOn = hasRemindersOn;
    this.reminderTimeString = reminderTime;
  }

  @DynamoDBHashKey(attributeName="Id")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public IdentityType getType() {
    return type;
  }

  public String getLastUpdateDateTimeString() {
    return lastUpdateDateTimeString;
  }

  public DateTime getLastUpdateDateTimeg() {
    if (lastUpdateDateTime == null)
      lastUpdateDateTime = new DateTime(lastUpdateDateTimeString);
    return lastUpdateDateTime;
  }

  public boolean hasSeenFtux() {
    return hasSeenFtux;
  }

  public boolean hasRemindersOn() {
    return hasRemindersOn;
  }

  public String getReminderTimeString() {
    return reminderTimeString;
  }

  public DateTime getReminderTime() {
    if (reminderTime == null)
      lastUpdateDateTime = new DateTime(reminderTimeString);
    return reminderTime;
  }

  private static class Builder {
    private final String id;
    private final IdentityType type;
    private String lastUpdateDateTime = "";
    private boolean hasSeenFtux = false;
    private boolean hasRemindersOn = false;
    private String reminderTime = "";

    public Builder(String id, IdentityType type) {
      this.id = id;
      this.type = type;
    }

    public Builder withLlastUpdateDateTime(String lastUpdateDateTime) {
      this.lastUpdateDateTime = lastUpdateDateTime;
      return this;
    }

    public Builder withHasSeenFtux(boolean hasSeenFtux) {
      this.hasSeenFtux = hasSeenFtux;
      return this;
    }

    public Builder withHasRemindersOn(boolean hasRemindersOn) {
      this.hasRemindersOn = hasRemindersOn;
      return this;
    }

    public Builder withReminderTime(String reminderTime) {
      this.reminderTime = reminderTime;
      return this;
    }

    public UserRecord build() {
      return new UserRecord(id, type, lastUpdateDateTime, hasSeenFtux, hasRemindersOn, reminderTime);
    }
  }

  // Parse Item, throwing exceptions where necessary.
  public static UserRecord fromItem(Item input) {
    Builder builder = new Builder(
        (String)input.get("id"),
        IdentityType.decode((String)input.get("idType")));

    // Parse optional fields.




    return new UserRecord;
  }

  @Override
  public Item toItem() {
    return null;
  }
}
