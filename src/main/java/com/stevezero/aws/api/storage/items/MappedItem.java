package com.stevezero.aws.api.storage.items;

/**
 * Describes a POJO mapped to a DynamoDb Item.
 */
public interface MappedItem {

  /**
   * @return a URL-safe String key that uniquely identifies this resource.
   */
  public String getKey();

}
