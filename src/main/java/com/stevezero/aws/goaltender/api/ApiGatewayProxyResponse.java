package com.stevezero.aws.goaltender.api;

import com.stevezero.aws.goaltender.http.StatusCode;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Modified from https://github.com/gabrielle-anderson/aws-lambda-proxy-java.  Thank you Gabrielle!
 */
public class ApiGatewayProxyResponse {
  private int statusCode;
  private Map<String, String> headers;
  private String body;
  private boolean isBase64Encoded;

  public int getStatusCode() {
    return statusCode;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public String getBody() {
    return body;
  }

  public boolean getIsBase64Encoded() {
    return isBase64Encoded;
  }

  private ApiGatewayProxyResponse(Builder builder) {
    this.statusCode = builder.statusCode.getCode();
    this.headers = builder.headers;
    this.body = builder.body;
    this.isBase64Encoded = builder.isBase64Encoded;
  }

  /**
   * Builder class for the response.
   */
  public static class Builder {
    private StatusCode statusCode = StatusCode.OK;
    private Map<String, String> headers = new HashMap();
    private String body = "";
    private boolean isBase64Encoded = false;

    public Builder withStatusCode(StatusCode statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public Builder withHeaders(Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public Builder withBody(String body) {
      this.body = body;
      return this;
    }

    public Builder withBase64Encoded(boolean base64Encoded) {
      isBase64Encoded = base64Encoded;
      return this;
    }

    public ApiGatewayProxyResponse build() {
      return new ApiGatewayProxyResponse(this);
    }
  }

  public String toJSONString() {
    JSONObject responseJson = new JSONObject();

    JSONObject headerJson = new JSONObject();
    for (String key : headers.keySet()) {
      headerJson.put(key, headers.get(key));
    }

    responseJson.put("isBase64Encoded", false);
    responseJson.put("statusCode", statusCode);
    responseJson.put("headers", headerJson);

    // TODO: may need more logic around body.
    responseJson.put("body", body);
    return responseJson.toJSONString();
  }

  @Override
  public String toString() {
    return "ApiGatewayProxyResponse{" +
        "statusCode=" + statusCode +
        ", headers=" + headers +
        ", body='" + body + '\'' +
        ", isBase64Encoded=" + isBase64Encoded +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ApiGatewayProxyResponse)) return false;

    ApiGatewayProxyResponse response = (ApiGatewayProxyResponse) o;

    if (statusCode != response.statusCode) return false;
    if (isBase64Encoded != response.isBase64Encoded) return false;
    if (headers != null ? !headers.equals(response.headers) : response.headers != null) return false;
    return body != null ? body.equals(response.body) : response.body == null;
  }

  @Override
  public int hashCode() {
    int result = statusCode;
    result = 31 * result + (headers != null ? headers.hashCode() : 0);
    result = 31 * result + (body != null ? body.hashCode() : 0);
    result = 31 * result + (isBase64Encoded ? 1 : 0);
    return result;
  }
}
