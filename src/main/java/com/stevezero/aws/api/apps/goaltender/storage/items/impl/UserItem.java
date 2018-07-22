package com.stevezero.aws.api.apps.goaltender.storage.items.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.storage.items.MappedItem;

/**
 * Mapped POJO for a Dynamo UserResource item.
 */
@DynamoDBTable(tableName="goaltender-user")
public class UserItem implements MappedItem {

  // Mapped.
  private String lastUpdateDateTimeString;
  private boolean hasSeenFtux;
  private boolean hasRemindersOn;
  private String reminderTimeString;
  private String key;

  // Not mapped.
  private UserId userId;

  @DynamoDBHashKey(attributeName="key")
  public String getKey() {
    return this.key;
  }
  public void setKey(String key) {
    this.key = key;
  }

  @DynamoDBHashKey(attributeName="lastUpdateDateTimeString")
  public String getLastUpdateDateTimeString() {
    return lastUpdateDateTimeString;
  }
  public void setLastUpdateDateTimeString(String lastUpdateDateTimeString) {
    this.lastUpdateDateTimeString = lastUpdateDateTimeString;
  }

  @DynamoDBHashKey(attributeName="hasSeenFtux")
  public boolean hasSeenFtux() {
    return hasSeenFtux;
  }
  public void setHasSeenFtux(boolean hasSeenFtux) {
    this.hasSeenFtux = hasSeenFtux;
  }

  @DynamoDBHashKey(attributeName="hasRemindersOn")
  public boolean hasRemindersOn() {
    return hasRemindersOn;
  }
  public void setHasRemindersOn(boolean hasRemindersOn) {
    this.hasRemindersOn = hasRemindersOn;
  }

  @DynamoDBHashKey(attributeName="reminderTimeString")
  public String getReminderTimeString() {
    return reminderTimeString;
  }
  public void setReminderTimeString(String reminderTimeString) {
    this.reminderTimeString = reminderTimeString;
  }


  // Unmapped utility getter
  // Note: we can safely depend that the key is defined, as it's the primary key in Dynamo.
  public UserId getUserId() throws InvalidResourceIdException {
    if (userId == null)
      this.userId = UserId.fromEncoded(this.key);
    return userId;
  }
}
