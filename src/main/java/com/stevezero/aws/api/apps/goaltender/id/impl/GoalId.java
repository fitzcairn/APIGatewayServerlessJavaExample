package com.stevezero.aws.api.apps.goaltender.id.impl;

import com.stevezero.aws.api.id.IdentityType;
import com.stevezero.aws.api.id.ResourceId;

/**
 * Pojo for a GoalId.
 * TODO: to enforce uniqueness, combine userId with the GoalId from the client.
 */
public class GoalId implements ResourceId {
  private final String id;

  public GoalId(String id) {
    this.id = id;
  }

  @Override
  public String toEncoded() {
    return id;
  }
}