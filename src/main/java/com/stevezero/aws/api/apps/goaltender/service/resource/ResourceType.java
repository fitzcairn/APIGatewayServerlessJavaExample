package com.stevezero.aws.api.apps.goaltender.service.resource;

public enum ResourceType {
  USER("user"),
  GOAL("goal");

  private final String code;

  ResourceType(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return code;
  }

  public static ResourceType decode(String encodedString) {
    for (ResourceType type : ResourceType.values()) {
      if (type.code.equalsIgnoreCase(encodedString)) {
        return type;
      }
    }
    throw new IllegalArgumentException();
  }
}
