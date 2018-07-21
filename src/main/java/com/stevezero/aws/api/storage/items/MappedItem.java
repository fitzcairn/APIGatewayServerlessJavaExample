package com.stevezero.aws.api.goaltender.storage.items;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;

/**
 * Describes a POJO mapped to a DynamoDb Item.
 */
public interface MappedItem {

  /**
   * @return a URL-safe String key that uniquely identifies this resource.
   */
  public String getKey();

}
