package com.stevezero.aws.goaltender.storage;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.stevezero.aws.goaltender.common.UserId;
import com.stevezero.aws.goaltender.storage.items.UserItem;

public class UserStorage {
  private final DynamoDB dynamoDb;
  private final DynamoDBMapper dynamoDbMapper;
  private final AmazonDynamoDBClient client;
  private static final String TABLE = "goaltender-user";

  // TODO: Can we hit a VIP or something similar?
  private static final Regions REGION = Regions.US_WEST_2;

  public UserStorage() {
    this.client = new AmazonDynamoDBClient();
    client.setRegion(Region.getRegion(REGION));
    this.dynamoDb = new DynamoDB(client);
    this.dynamoDbMapper = new DynamoDBMapper(client);
  }

  /**
   * Can return null if no such user exists.
   * @param userId the ID of the user to return.
   * @return the UserItem object, or null if not found.
   */
  public UserItem getUser(UserId userId) {
    return dynamoDbMapper.load(UserItem.class, userId.toEncoded(),
        new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT));
  }
}
