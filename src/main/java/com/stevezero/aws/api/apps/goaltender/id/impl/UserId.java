package com.stevezero.aws.api.apps.goaltender.id.impl;

import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.id.IdentityType;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Base64;

/**
 * UserId pojo, with translation methods.
 */
public class UserId implements ApiResourceId {
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


  /* base64IdString is a URL-safe base64 encoded version of:
  {
    t: "g", // IdentityType
    i: "1234", // Token
  }
*/
  public UserId(String base64IdString) throws InvalidResourceIdException {
    try {
      // Decode the string, propagating the exception if something went wrong.
      String jsonIdString = new String(DECODER.decode(base64IdString));
      JSONObject userIdJson = (JSONObject)JSON_PARSER.parse(jsonIdString);

      // Now parse the resulting JSON.
      // TODO: identity validation, if we're to do something more than store items against this ID.
      this.id = (String)userIdJson.get(ID_KEY);
      this.idType = IdentityType.decode((String)userIdJson.get(TYPE_KEY));
    } catch (ParseException | IllegalArgumentException e) {
      throw new InvalidResourceIdException(base64IdString);
    }
  }

  @Override
  public String toBase64String() {
    JSONObject userIdJson = new JSONObject();
    userIdJson.put(TYPE_KEY, this.idType.toString());
    userIdJson.put(ID_KEY, this.id);
    String JSONString = userIdJson.toJSONString();
    return ENCODER.encodeToString(JSONString.getBytes());
  }
}
