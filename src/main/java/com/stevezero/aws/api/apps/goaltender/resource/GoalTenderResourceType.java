package com.stevezero.aws.api.apps.goaltender.resource;

public enum GoalTenderResourceType {
  USER,
  GOAL;

  @Override
  public String toString() {
    return this.name().toLowerCase();
  }
}
