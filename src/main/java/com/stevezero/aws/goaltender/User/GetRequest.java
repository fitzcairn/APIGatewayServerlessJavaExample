package com.stevezero.aws.goaltender.User;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetRequest implements RequestHandler<String, String>{

  public String handleRequest(String userId, Context context) {
    return "GET " + userId;
  }
}
