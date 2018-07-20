package com.stevezero.aws.goaltender.user.data;

import java.io.IOException;

/**
 * Pojo for a user ID scoped to service.
 */
public class UserId {
  private final IdentityType type;
  private final String id;

  private UserId(IdentityType type, String id) {
    this.type = type;
    this.id = id;
  }

  public enum IdentityType {
    GOOGLE("g"),
    FACEBOOK("f");

    private final String code;

    IdentityType(String code) {
      this.code = code;
    }

    @Override
    public String toString() {
      return code;
    }
  }

  // TODO: implement string encoding... like a base64 encoded block.
  @Override
  public String toString() {
    return id;
  }

  // TODO: actually parse the path string and decode the ID.
  public static UserId fromPathString(String id) throws IOException {
    return new UserId(IdentityType.GOOGLE, id);
  }
}
