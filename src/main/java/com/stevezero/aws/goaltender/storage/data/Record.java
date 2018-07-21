package com.stevezero.aws.goaltender.storage.data;

import com.amazonaws.services.dynamodbv2.document.Item;

/**
 * Interface for a Dynamo record.
 */
public interface Record {

  // From Pojo to Item.
  public Item toItem();
}
