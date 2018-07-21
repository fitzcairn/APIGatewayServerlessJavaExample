package com.stevezero.aws.goaltender.common.id;

/**
 * Describes a Resource ID in the API and also in Dynamo DB.  For example, a User ID.
 */
public interface ResourceId {

  /**
   * @return a URL-safe, opaque encoding of this resource ID.
   */
  public String toEncoded();
}
