package com.stevezero.aws.api.apps.goaltender.storage.service.impl;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.id.ResourceId;
import com.stevezero.aws.api.storage.items.MappedItem;
import com.stevezero.aws.api.storage.service.StorageService;


/**
 * Lightweight storage service for UserResource resource.  Encapsulates dealing with DynamoDb.
 */
public class UserStorageService implements StorageService {
  private final DynamoDBMapper dynamoDbMapper;

  public UserStorageService() {
    // TODO: Can we hit a VIP or something similar instead of hard-coding region?
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.US_WEST_2)
        .build();
    this.dynamoDbMapper = new DynamoDBMapper(client);
  }

  /**
   * Update a user, overwriting fields already there, or creating if not there.
   * @param item a full UserItem instance.
   */
  @Override
  public void update(MappedItem item) {
    UserItem userItem = (UserItem)item;
    dynamoDbMapper.save(userItem,
        new DynamoDBMapperConfig(DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES));
  }

  /**
   * Can return null if no such user exists.
   * @param id the ID of the user to return.
   * @return the UserItem object, or null if not found.
   */
  @Override
  public MappedItem get(ResourceId id) {
    return dynamoDbMapper.load(UserItem.class, id.toEncoded(),
        new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT));
  }
}
