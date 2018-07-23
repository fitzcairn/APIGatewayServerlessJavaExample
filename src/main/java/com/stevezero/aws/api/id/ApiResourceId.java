package com.stevezero.aws.api.id;

import org.json.simple.parser.JSONParser;

import java.util.Base64;

/**
 * Describes a Resource ID in the API and also in Dynamo DB.  For example, a UserResource ID.
 */
public interface ApiResourceId {
  Base64.Encoder ENCODER = Base64.getUrlEncoder();
  Base64.Decoder DECODER = Base64.getUrlDecoder();
  JSONParser JSON_PARSER = new JSONParser();

  /**
   * @return a URL-safe, opaque encoding of this resource ID.
   */
  public String toBase64String();
}
