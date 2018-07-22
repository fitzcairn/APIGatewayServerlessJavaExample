package com.stevezero.aws.api.apps.goaltender.storage.items.impl;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel.DynamoDBAttributeType;
import com.stevezero.aws.api.storage.items.MappedItem;

/**
 * Mapped POJO for a DynamoDb UserResource item.
 */
@DynamoDBTable(tableName="goaltender-user")
public class UserItem implements MappedItem {
  private String lastUpdateDateTimeString;
  private boolean hasSeenFtux;
  private boolean hasRemindersOn;
  private String reminderTimeString;
  private String key;

  @DynamoDBHashKey(attributeName="key")
  @DynamoDBAutoGeneratedKey
  public String getKey() {
    return this.key;
  }
  public void setKey(String key) {
    this.key = key;
  }

  @DynamoDBAttribute(attributeName="lastUpdateDateTimeString")
  public String getLastUpdateDateTimeString() {
    return lastUpdateDateTimeString;
  }
  public void setLastUpdateDateTimeString(String lastUpdateDateTimeString) {
    this.lastUpdateDateTimeString = lastUpdateDateTimeString;
  }

  @DynamoDBTyped(DynamoDBAttributeType.BOOL)
  @DynamoDBAttribute(attributeName="getHasSeenFtux")
  public boolean getHasSeenFtux() {
    return hasSeenFtux;
  }
  public void setHasSeenFtux(boolean hasSeenFtux) {
    this.hasSeenFtux = hasSeenFtux;
  }

  @DynamoDBTyped(DynamoDBAttributeType.BOOL)
  @DynamoDBAttribute(attributeName="getHasRemindersOn")
  public boolean getHasRemindersOn() {
    return hasRemindersOn;
  }
  public void setHasRemindersOn(boolean hasRemindersOn) {
    this.hasRemindersOn = hasRemindersOn;
  }

  @DynamoDBAttribute(attributeName="reminderTimeString")
  public String getReminderTimeString() {
    return reminderTimeString;
  }
  public void setReminderTimeString(String reminderTimeString) {
    this.reminderTimeString = reminderTimeString;
  }
}