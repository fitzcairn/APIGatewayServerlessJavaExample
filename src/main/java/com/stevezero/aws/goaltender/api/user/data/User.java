package com.stevezero.aws.goaltender.api.user.data;

/**
 * Simple pojo representing a user.
 */
public class User {
  private UserId id;

  private User(UserId id) {
    this.id = id;
  }

  /**
   * Builder class for the user.
   */
  public static class UserBuilder {
    private UserId id;
    public User build() {
      return new User(id);
    }
  }

}
