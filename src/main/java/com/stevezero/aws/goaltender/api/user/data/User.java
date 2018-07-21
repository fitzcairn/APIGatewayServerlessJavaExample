package com.stevezero.aws.goaltender.api.user.data;

import com.stevezero.aws.goaltender.common.UserId;
import com.stevezero.aws.goaltender.storage.items.UserItem;

/**
 * Simple pojo representing a user.
 */
public class User {
  private UserId id;

  private User(UserId id) {
    this.id = id;
  }

  public static User fromUserItem(UserItem userItem) {
    return new User(userItem.getUserId());
  }

}
