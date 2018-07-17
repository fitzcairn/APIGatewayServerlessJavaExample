package com.stevezero.aws.goaltender.lambda;

import java.io.IOException;
import java.util.HashMap;

import com.stevezero.aws.goaltender.User.GetRequest;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * Tests for the GetRequest class.
 */
public class GetRequestTest {
  private static Object input;

  @BeforeClass
  public static void createInput() throws IOException {
  }

  private Context createContext() {
    TestContext ctx = new TestContext();

    // TODO: customize your context here if needed.
    ctx.setFunctionName("LambdaForm");

    return ctx;
  }

  @Test
  public void testHandleRequest() {
    GetRequest requestHandler = new GetRequest();
    Context ctx = createContext();

    Object output = requestHandler.handleRequest("Test ID", ctx);

    // TODO: validate output here if needed.
    if (output != null) {
      System.out.println(output.toString());
    }
  }
}