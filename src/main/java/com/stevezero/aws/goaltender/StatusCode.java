package com.stevezero.aws.goaltender;

public enum StatusCode {
  OK(200, "Ok"),
  SERVER_ERROR(400, "Unknown Server Error"),
  NOT_FOUND(404, "Not found"),
  NOT_ALLOWED(405, "Method not allowed.");

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
