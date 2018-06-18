package com.adstats.stats;

public enum GroupByField {
  BROWSER,
  OS;

  String getKey() {
    return name().toLowerCase();
  }
}
