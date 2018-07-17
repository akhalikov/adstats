package com.adstats.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BasicStats {
  @JsonProperty
  private Interval interval;

  @JsonProperty
  private Stats stats;

  public BasicStats() {
  }

  public BasicStats(Interval interval, Stats stats) {
    this.interval = interval;
    this.stats = stats;
  }

  public Interval getInterval() {
    return interval;
  }

  public Stats getStats() {
    return stats;
  }
}
