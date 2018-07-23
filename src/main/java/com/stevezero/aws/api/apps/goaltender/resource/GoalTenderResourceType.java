package com.stevezero.aws.api.apps.goaltender.resource;

public enum ResourceType {
  USER,
  GOAL;

  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
