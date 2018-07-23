package com.stevezero.aws.api;

import com.stevezero.aws.api.exceptions.InvalidApiInvocation;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ApiPathTest {

  private enum TestResource {
    APPLE,
    PEAR,
    PLUM,
  }

  @Test
  public void testPathSingleOk() throws InvalidApiInvocation {
    ApiPath testPath = ApiPath.of("/apple/1234", TestResource.class);

    assertEquals(TestResource.APPLE, testPath.getLastResource());
    assertEquals("1234", testPath.getIdFor(TestResource.APPLE));
    assertNull(testPath.getIdFor(TestResource.PEAR));
  }

  @Test
  public void testPathMultipleOk() throws InvalidApiInvocation {
    ApiPath testPath = ApiPath.of("/apple/1234/pear/5678", TestResource.class);

    assertEquals(TestResource.PEAR, testPath.getLastResource());
    assertEquals("1234", testPath.getIdFor(TestResource.APPLE));
    assertEquals("5678", testPath.getIdFor(TestResource.PEAR));
    assertNull(testPath.getIdFor(TestResource.PLUM));
  }

  @Test
  public void testPathNoIdOk() throws InvalidApiInvocation {
    ApiPath testPath = ApiPath.of("/apple", TestResource.class);
    assertNull(testPath.getIdFor(TestResource.APPLE));
  }

  @Test
  public void testPathNoIdTrailingSlashOk() throws InvalidApiInvocation {
    ApiPath testPath = ApiPath.of("/apple/", TestResource.class);
    assertNull(testPath.getIdFor(TestResource.APPLE));
  }

  @Test(expected = InvalidApiInvocation.class)
  public void testPathInvalidNoResource() throws InvalidApiInvocation {
    ApiPath.of("/", TestResource.class);
  }
}