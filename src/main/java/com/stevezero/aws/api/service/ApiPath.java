package com.stevezero.aws.api.service;

import com.stevezero.aws.api.exceptions.InvalidApiInvocation;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class representing a parsed API invocation path.
 */
public class ApiPath <T extends Enum> {
  private final String path;
  private final T lastResource;
  private final Map<T, String> resourceIdMap;

  private ApiPath(String path, T lastResource, Map<T, String> resourceIdMap) {
    this.path = path;
    this.lastResource = lastResource;
    this.resourceIdMap = resourceIdMap;
  }

  public T getLastResource() {
    return lastResource;
  }

  /**
   * @param resourceType for the id requested.
   * @return the String id for the resource.
   */
  public String getIdFor(T resourceType) {
    return resourceIdMap.get(resourceType);
  }

  /**
   * @return the original path string.
   */
  public String getPath() {
    return path;
  }

  /**
   * Parse the API path into resources and identifiers.  Will throw exceptions on malformed paths.
   * @param pathString the invoking API path.
   * @return a full ApiPath object.
   * @throws InvalidApiInvocation on malformed paths.
   */
  public static <I extends Enum<I>> ApiPath<I> of(String pathString, final Class<I> typesClass)
      throws InvalidApiInvocation {
    if (pathString == null)
      throw new InvalidApiInvocation();

    // Ex: /RESOURCE1/ID1/RESOURCE2/ID2
    String[] components = pathString.split("/");

    try {
      Map<I, String> resourceIdMap;
      resourceIdMap = new EnumMap<I, String>(typesClass);
      I resource = null;

      // Expect that we see (resource, ID) repeated throughout, starting at element 1 (not 0).
      for (int i = 1; i < components.length; ) {
        // Expected: resource.
        resource = (I) I.valueOf(typesClass, components[i++].toUpperCase());
        // Optional: id
        String id = null;
        if (i < components.length)
          id = components[i++];

        // Put in map, continue.
        resourceIdMap.put(resource, id);
      }

      // Construct and return the parsed path, assuming weh ave one.
      if (resource != null)
        return new ApiPath<I>(pathString, resource, resourceIdMap);
      else
        throw new InvalidApiInvocation(pathString);
    } catch (IllegalArgumentException e) {
      throw new InvalidApiInvocation(pathString);
    }
  }
}
