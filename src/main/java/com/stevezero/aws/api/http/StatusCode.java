package com.stevezero.aws.api.http;

public enum StatusCode {
  OK(200, "Ok"),
  BAD_REQUEST(400, "Bad request"),
  NOT_FOUND(404, "Not found"),
  NOT_ALLOWED(405, "MethodType not allowed."),
  SERVER_ERROR(500, "Unknown Server Error");

  private final int code;
  private final String description;

  StatusCode(int code, String description) {
    this.code = code;
    this.description = description;
  }

  @Override
  public String toString() {
    return Integer.toString(code);
  }

  public int getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }
}
