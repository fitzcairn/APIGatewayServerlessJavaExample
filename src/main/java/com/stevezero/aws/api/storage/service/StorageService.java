package com.stevezero.aws.api.storage.service;

import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.storage.items.MappedItem;

/**
 * Interface for a service interacting with backend storage.
 */
public interface StorageService {

  /**
   * Update an object in the resource store, overwriting fields already there, or creating if not there.
   *
   * @param item a MappedItem instance.
   */
  public void update(MappedItem item);

  /**
   * Can return null if no such resource exists.
   *
   * @param id the ID of the user to return.
   * @return the MappedItem, or null if not found.
   */
  public MappedItem get(ApiResourceId id);

}
