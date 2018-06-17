package com.akhalikov.adstats.stats;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class BasicStats {
  @JsonProperty
  private Interval interval;

  @JsonProperty
  private Stats stats;

  public BasicStats() {
  }

  BasicStats(Interval interval, Stats stats) {
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
