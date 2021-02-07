package com.luo.mybatis.binding;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {
  private Map<String,MapperMethod> knownMappers = new HashMap<String, MapperMethod>();

  public Map<String, MapperMethod> getKnownMappers() {
    return knownMappers;
  }

  public void setKnownMappers(Map<String, MapperMethod> knownMappers) {
    this.knownMappers = knownMappers;
  }
}
