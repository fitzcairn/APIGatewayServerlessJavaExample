package com.stevezero.aws.api.apps.goaltender.storage.service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Implementation of Dynamo-backed storage.
 */
public abstract class DynamoStorageService implements StorageService {
  protected final DynamoDBMapper dynamoDbMapper;

  public DynamoStorageService() {
    // TODO: Can we hit a VIP or something similar instead of hard-coding region?
    AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
        .withRegion(Regions.US_WEST_2)
        .build();
    this.dynamoDbMapper = new DynamoDBMapper(client);
  }


}
