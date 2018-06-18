package com.adstats.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Stats {
  @JsonProperty
  private long deliveries;

  @JsonProperty
  private long clicks;

  @JsonProperty
  private long installs;

  public Stats() {
  }

  Stats(long deliveries, long clicks, long installs) {
    this.deliveries = deliveries;
    this.clicks = clicks;
    this.installs = installs;
  }

  public long getDeliveries() {
    return deliveries;
  }

  public long getClicks() {
    return clicks;
  }

  public long getInstalls() {
    return installs;
  }

  @Override
  public String toString() {
    return "Stats{" +
        "deliveries=" + deliveries +
        ", clicks=" + clicks +
        ", installs=" + installs +
        '}';
  }
}
