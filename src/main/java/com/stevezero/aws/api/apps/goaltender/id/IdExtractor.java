package com.stevezero.aws.api.apps.goaltender.id;

import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.service.resource.ResourceType;
import com.stevezero.aws.api.exceptions.InvalidApiResourceName;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.id.ResourceId;

/**
 * Utility functions for working with paths containing IDs.
 */
public class IdExtractor {
  private IdExtractor() {}

  /**
   * Extract the resource ID from the path.
   * @param pathString
   * @return
   * @throws InvalidApiResourceName
   * @throws InvalidResourceIdException
   */
  public static ResourceId extractIdFromPath(String pathString, ResourceType resourceType)
      throws InvalidApiResourceName, InvalidResourceIdException {
    // Ex: /user/ID
    String[] components = pathString.split("/");

    // Make sure we have expected input.
    if (components.length != 3 || !resourceType.toString().equals(components[1]))
      throw new InvalidApiResourceName("Invalid API call.");

    switch(resourceType) {
      case USER:
        return UserId.fromEncoded(components[2]);
      case GOAL:
        // TODO: implement.
      default:
        throw new InvalidApiResourceName("Invalid API call.");
    }
  }
}
