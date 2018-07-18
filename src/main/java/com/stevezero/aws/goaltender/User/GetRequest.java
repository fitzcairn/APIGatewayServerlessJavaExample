package com.stevezero.aws.goaltender.User;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetRequest implements RequestHandler<Object, String> {

  public String handleRequest(Object input, Context context) {
    context.getLogger().log("Input: " + input);

    return "GET UserId fired!";
  }
}
