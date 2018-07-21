package com.stevezero.aws.goaltender.common;

import com.stevezero.aws.goaltender.api.exceptions.InvalidUserIdException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Base64;

/**
 * Simple UserId pojo.
 */
public class UserId {
  private static final Base64.Encoder ENCODER = Base64.getUrlEncoder();
  private static final Base64.Decoder DECODER = Base64.getUrlDecoder();
  private static final JSONParser JSON_PARSER = new JSONParser();

  private static final String TYPE_KEY = "t";
  private static final String ID_KEY = "i";

  private final IdentityType idType;
  private final String id;

  public UserId(String id, IdentityType idType) {
    this.id = id;
    this.idType = idType;
  }

  public IdentityType getType() {
    return idType;
  }

  public String getId() {
    return id;
  }

  public String toEncoded() {
    JSONObject userIdJson = new JSONObject();
    userIdJson.put(TYPE_KEY, this.idType.toString());
    userIdJson.put(ID_KEY, this.id);
    String JSONString = userIdJson.toJSONString();
    return ENCODER.encodeToString(JSONString.getBytes());
  }

  /* base64IdString is a URL-safe base64 encoded version of:
      {
        t: "g", // IdentityType
        i: "1234", // Token
      }
   */
  public static UserId fromEncoded(String base64IdString) throws IllegalArgumentException {
    try {
      // Decode the string, propagating the exception if something went wrong.
      String jsonIdString = new String(DECODER.decode(base64IdString));
      JSONObject userIdJson = (JSONObject) JSON_PARSER.parse(jsonIdString);

      // Now parse the resulting JSON.
      // TODO: identity validation, if we're to do something more than store items against this ID.
      return new UserId(
          (String)userIdJson.get(ID_KEY),
          IdentityType.decode((String)userIdJson.get(TYPE_KEY)));

    } catch (ParseException | IllegalArgumentException e) {
      throw new IllegalArgumentException();
    }
  }
}
