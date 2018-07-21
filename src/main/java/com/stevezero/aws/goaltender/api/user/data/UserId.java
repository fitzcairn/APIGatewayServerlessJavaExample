package com.stevezero.aws.goaltender.api.user.data;

import com.stevezero.aws.goaltender.common.IdentityType;
import com.stevezero.aws.goaltender.api.exceptions.InvalidAPIResource;
import com.stevezero.aws.goaltender.api.exceptions.InvalidUserIdException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Base64;

/**
 * Utility functions and representation for a user ID scoped to service.
 */
public class UserId {
  private final IdentityType type;
  private final String id;

  private static final Base64.Encoder ENCODER = Base64.getUrlEncoder();
  private static final Base64.Decoder DECODER = Base64.getUrlDecoder();
  private static final JSONParser JSON_PARSER = new JSONParser();

  private static final String PATH_KEY = "user";
  private static final String TYPE_KEY = "t";
  private static final String ID_KEY = "i";

  public String getId() {
    return id;
  }

  public IdentityType getType() {
    return type;
  }

  // Return this UserId as a JSON string.
  @Override
  public String toString() {
    JSONObject userIdJson = new JSONObject();
    userIdJson.put(TYPE_KEY, this.type.toString());
    userIdJson.put(ID_KEY, this.id);
    return userIdJson.toJSONString();
  }

  // Class is uninstantiable without static helpers.
  private UserId(IdentityType type, String id) {
    this.type = type;
    this.id = id;
  }

  public static UserId fromPathString(String idPathString) throws InvalidAPIResource, InvalidUserIdException {
    // Expect: /user/ID
    String[] components = idPathString.split("/");

    // Make sure we have expected input.
    if (components.length != 3 || !PATH_KEY.equals(components[1]))
      throw new InvalidAPIResource("Invalid API call.");

    return fromIdString(components[2]);
  }

  /* base64IdString is a URL-safe base64 encoded version of:
      {
        type: "g", // IdentityType
        id: "1234", // Token
      }
   */
  public static UserId fromIdString(String base64IdString) throws InvalidUserIdException {
    // Parse the JSON.
    try {
      // Decode the string, propagating the exception if something went wrong.
      String jsonIdString = new String(DECODER.decode(base64IdString));
      JSONObject userIdJson = (JSONObject) JSON_PARSER.parse(jsonIdString);

      // TODO: identity validation, if we're to do something more than store data against this ID.
      return new UserId(
          IdentityType.decode((String)userIdJson.get(TYPE_KEY)),
          (String)userIdJson.get(ID_KEY));

    } catch (ParseException | IllegalArgumentException e) {
      throw new InvalidUserIdException(base64IdString);
    }
  }
}
