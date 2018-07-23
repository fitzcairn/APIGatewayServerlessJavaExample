package com.stevezero.aws.api.apps.goaltender.id;

import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.resource.ResourceType;
import com.stevezero.aws.api.exceptions.InvalidApiResourceName;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.id.IdentityType;
import com.stevezero.aws.api.id.ApiResourceId;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Base64;

/**
 * Factory class for ResourceIds.
 */
public class PathIdExtractor {
  private PathIdExtractor() {}

  /**
   * Extract the resource ID from the path.
   * @param pathString
   * @return
   * @throws InvalidApiResourceName
   * @throws InvalidResourceIdException
   */
  public static ApiResourceId extractIdFromPath(String pathString, ResourceType resourceType)
      throws InvalidApiResourceName, InvalidResourceIdException {
    // Ex: /user/ID
    String[] components = pathString.split("/");

    // Make sure we have expected input.
    if (components.length != 3 || !resourceType.toString().equals(components[1]))
      throw new InvalidApiResourceName("Invalid API call.");

    switch(resourceType) {
      case USER:
        return new UserId(components[2]);
      case GOAL:
        // TODO: implement.
      default:
        throw new InvalidApiResourceName("Invalid API call.");
    }
  }
}
