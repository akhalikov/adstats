package com.adstats.stats;

public enum Metric {
  DELIVERY,
  CLICK,
  INSTALL;

  public String getKey() {
    return name().toLowerCase();
  }
}
