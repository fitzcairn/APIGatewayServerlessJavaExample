package com.stevezero.aws.goaltender.storage;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.stevezero.aws.goaltender.storage.data.UserRecord;

public class UserStorage {
  private DynamoDB dynamoDb;
  private static final String TABLE = "goaltender-user";

  // TODO: Can we hit a VIP or something similar?
  private static final Regions REGION = Regions.US_WEST_2;

  public UserStorage() {
    AmazonDynamoDBClient client = new AmazonDynamoDBClient();
    client.setRegion(Region.getRegion(REGION));
    this.dynamoDb = new DynamoDB(client);
  }

  private PutItemOutcome writeUser(UserRecord user)
      throws ConditionalCheckFailedException {
    return this.dynamoDb.getTable(TABLE)
        .putItem(
            new PutItemSpec().withItem(new Item()
                .withString("firstName", personRequest.getFirstName())
                .withString("lastName", personRequest.getLastName());
  }
}
