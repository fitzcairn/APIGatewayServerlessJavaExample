package com.stevezero.aws.api.goaltender.common;

/**
 * Supported identity providers.
 */
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

  public static IdentityType decode(String encodedString) {
    for (IdentityType type : IdentityType.values()) {
      if (type.code.equalsIgnoreCase(encodedString)) {
        return type;
      }
    }
    throw new IllegalArgumentException();
  }
}