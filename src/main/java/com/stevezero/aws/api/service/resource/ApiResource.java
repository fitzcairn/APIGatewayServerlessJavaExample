package com.stevezero.aws.api.service.resource;

import com.stevezero.aws.api.storage.items.MappedItem;

/**
 * A resource in the API.  Acts as a translation layer between frontend clients and the backend storage.
 */
public interface ApiResource {

  /**
   * @return a JSON representation of this resource.
   */
  public String toJsonString();

  /**
   * @return a full MappedItem of this resource that can be persisted to storage.
   */
  public MappedItem toItem();
}
