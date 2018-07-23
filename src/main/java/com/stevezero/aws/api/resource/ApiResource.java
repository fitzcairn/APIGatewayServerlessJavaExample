package com.stevezero.aws.api.resource;

import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.storage.items.MappedItem;
import org.json.simple.parser.JSONParser;

/**
 * A resource in the API.  Acts as a translation layer between frontend clients and the backend storage.
 */
public interface ApiResource {
  JSONParser JSON_PARSER = new JSONParser();

  /**
   * @return the id for this resource.
   */
  public ApiResourceId getResourceId();

  /**
   * @return a JSON representation of this resource.
   */
  public String toJsonString();

  /**
   * @return a full MappedItem of this resource that can be persisted to storage.
   */
  public MappedItem toItem();
}
