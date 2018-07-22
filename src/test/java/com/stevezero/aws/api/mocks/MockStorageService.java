package com.stevezero.aws.api.mocks;

import com.stevezero.aws.api.id.ResourceId;
import com.stevezero.aws.api.storage.items.MappedItem;
import com.stevezero.aws.api.storage.service.StorageService;

import java.util.HashMap;
import java.util.Map;

/**
 * Super simple controllable mock storage service.
 */
public class MockStorageService implements StorageService {
  private final Map<String, MappedItem> storageMap = new HashMap();

  // For Testing
  public void reset() {
    storageMap.clear();
  }

  // For testing
  public MockStorageService add(String id, MappedItem item) {
    storageMap.put(id, item);
    return this;
  }

  // For testing
  public Map<String, MappedItem> getMap() {
    return storageMap;
  }

  @Override
  public void update(MappedItem item) {
    // TODO: consider simulating update if not null behavior.
    storageMap.put(item.getKey(), item);
  }

  @Override
  public MappedItem get(ResourceId id) {
    return storageMap.get(id.toEncoded());
  }
}
