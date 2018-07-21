package com.stevezero.aws.goaltender.storage.items;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.stevezero.aws.goaltender.common.IdentityType;
import com.stevezero.aws.goaltender.common.UserId;
import org.joda.time.DateTime;

/**
 * Mapped class for a Dynamo User item.
 */
@DynamoDBTable(tableName="goaltender-user")
public class UserItem {

  // Mapped.
  private final String id;
  private final String idType;
  private String lastUpdateDateTimeString;
  private boolean hasSeenFtux;
  private boolean hasRemindersOn;
  private String reminderTimeString;

  // Not mapped.
  private DateTime lastUpdateDateTime;
  private DateTime reminderTime;
  private IdentityType identityType;
  private UserId userId;


  public UserItem(String id,
                  String idType) {
    this.id = id;
    this.idType = idType;
  }

  @DynamoDBHashKey(attributeName="key")
  public String getKey() {
    return getUserId().toEncoded();
  }

  @DynamoDBHashKey(attributeName="id")
  public String getId() {
    return id;
  }

  @DynamoDBHashKey(attributeName="idType")
  public String getIdType() {
    return idType;
  }

  @DynamoDBHashKey(attributeName="lastUpdateDateTime")
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

  @DynamoDBHashKey(attributeName="reminderTime")
  public String getReminderTimeString() {
    return reminderTimeString;
  }
  public void setReminderTimeString(String reminderTimeString) {
    this.reminderTimeString = reminderTimeString;
  }

  // Unmapped utility getters, with lazy init.
  public DateTime getReminderTime() {
    if (reminderTime == null)
      this.reminderTime = new DateTime(reminderTimeString);
    return reminderTime;
  }

  public DateTime getLastUpdateDateTime() {
    if (lastUpdateDateTime == null)
      this.lastUpdateDateTime = new DateTime(lastUpdateDateTimeString);
    return lastUpdateDateTime;
  }

  public IdentityType getIdentityType() {
    if (identityType == null)
      this.identityType = IdentityType.decode(idType);
    return identityType;
  }

  public UserId getUserId() {
    if (userId == null)
      this.userId = new UserId(this.id, getIdentityType());
    return userId;
  }
}
