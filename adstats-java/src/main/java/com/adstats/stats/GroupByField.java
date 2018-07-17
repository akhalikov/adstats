package com.adstats.stats;

public enum GroupByField {
  BROWSER,
  OS;

  public String getKey() {
    return name().toLowerCase();
  }
}
